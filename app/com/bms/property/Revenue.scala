package com.bms.property


import play.api.libs.functional.syntax._
import play.api.libs.json.{JsPath, Reads, Writes}

case class Revenues (
                      revenueType: String,
                      value: Option[Double],
                      vacancyrRate: Option[Double]
                    )

object Revenues{}
