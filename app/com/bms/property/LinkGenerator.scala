package com.bms.property

import play.api.mvc.Action

/**
  * Created by Adi on 2017-02-26.
  */
trait LinkGenerator[A] {
  def generateLink(a: A): String
}

//object LinkGenerator{
//  implicit object PropertyLinkGenerator extends LinkGenerator[Property]{
//    override def generateLink(p: Property) = "<a href=\"" + getPath(p.mls_no) +
//                                    "\" style=\"white-space:nowrap\"" +
//                                    " title=\"Open in Document Viewer\" " + ">" +
//                                    p.toString + "</a>"
//    def getPath(mls :String) = controllers.routes.ProspectPropertyController.getPropertyByMLS(mls)
//  }
//}