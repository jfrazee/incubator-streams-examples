package com.peoplepattern.streams.twitter.collection

import java.io.File
import java.nio.file.{Files, Paths}

import com.typesafe.config.{Config, ConfigFactory, ConfigParseOptions}
import org.apache.streams.examples.flink.twitter.{TwitterSpritzerPipelineConfiguration, TwitterUserInformationPipelineConfiguration}
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.streams.config.{ComponentConfigurator, StreamsConfiguration, StreamsConfigurator}
import org.apache.streams.examples.flink.twitter.collection.FlinkTwitterUserInformationPipeline
import org.apache.streams.hdfs.{HdfsConfiguration, HdfsReaderConfiguration, HdfsWriterConfiguration}
import org.scalatest.FlatSpec
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import org.slf4j.{Logger, LoggerFactory}

import scala.io.Source
import org.scalatest.Ignore
import org.scalatest.concurrent.Eventually._
import org.scalatest.time.{Seconds, Span}
import org.scalatest.time.SpanSugar._
import org.testng.annotations.Test

/**
  * Created by sblackmon on 3/13/16.
  */
class FlinkTwitterUserInformationPipelineIT extends FlatSpec {

  private val LOGGER: Logger = LoggerFactory.getLogger(classOf[FlinkTwitterUserInformationPipelineIT])

  import FlinkTwitterUserInformationPipeline._

  @Test
  def flinkTwitterUserInformationPipelineIT = {

    val reference: Config = ConfigFactory.load()
    val conf_file: File = new File("target/test-classes/FlinkTwitterUserInformationPipelineIT.conf")
    assert(conf_file.exists())
    val testResourceConfig: Config = ConfigFactory.parseFileAnySyntax(conf_file, ConfigParseOptions.defaults().setAllowMissing(false));

    val typesafe: Config = testResourceConfig.withFallback(reference).resolve()
    val streams: StreamsConfiguration = StreamsConfigurator.detectConfiguration(typesafe)
    val testConfig = new ComponentConfigurator(classOf[TwitterUserInformationPipelineConfiguration]).detectConfiguration(typesafe)

    setup(testConfig)

    val job = new FlinkTwitterUserInformationPipeline(config = testConfig)
    val jobThread = new Thread(job)
    jobThread.start
    jobThread.join

    eventually (timeout(30 seconds), interval(1 seconds)) {
      assert(Files.exists(Paths.get(testConfig.getDestination.getPath + "/" + testConfig.getDestination.getWriterPath)))
      assert(
        Source.fromFile(testConfig.getDestination.getPath + "/" + testConfig.getDestination.getWriterPath, "UTF-8").getLines.size
          > 500)
    }

  }

}
