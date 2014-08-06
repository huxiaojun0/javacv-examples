/*
 * Copyright (c) 2014 Jarek Sacha. All Rights Reserved.
 * Author's e-mail: jpsacha at gmail dot com
 */
package flycapture.examples.cpp

import org.bytedeco.javacpp.FlyCapture2._

/**
 * The FlyCapture2Test sample program is a simple program designed to report information related to all compatible
 * cameras attached to the host system, capture a series of images from a single camera,
 * record the amount of time taken to grab these images, then save the last image in the current directory.
 *
 * Example of using FlyCapture2 C++ API. Based on FlyCapture2Test.cpp example from FlyCapture SDK.
 */
object CameraPropertyInfoEx extends App {

  def runSingleCamera(guid: PGRGuid) {
    val numImages = 10

    // Connect to a camera
    val cam = new Camera()
    check(cam.Connect(guid), " - Connect")

    // Get the camera information
    val camInfo = new CameraInfo()
    check(cam.GetCameraInfo(camInfo), " - GetCameraInfo")
    printCameraInfo(camInfo)

    printPropertyInfo(cam)

    // Disconnect the camera
    check(cam.Disconnect(), " - Disconnect")
  }


  printBuildInfo()

  val busMgr = new BusManager()
  val numCameras = Array[Int](0)
  check(busMgr.GetNumOfCameras(numCameras), " - GetNumOfCameras")
  println("Number of cameras detected: " + numCameras(0))

  for (i <- 0 until numCameras(0)) {
    val guid = new PGRGuid()
    check(busMgr.GetCameraFromIndex(i, guid), " - GetCameraFromIndex")

    runSingleCamera(guid)
  }

  println("Done!")
}