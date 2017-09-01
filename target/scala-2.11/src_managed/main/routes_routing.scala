// @SOURCE:/Users/461967/dev/toggle-service/conf/routes
// @HASH:4f034d09cfbc834baa9fa8d5910645b9063e9c79
// @DATE:Thu May 25 14:43:55 EDT 2017


import scala.language.reflectiveCalls
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString

object Routes extends Router.Routes {

import ReverseRouteContext.empty

private var _prefix = "/"

def setPrefix(prefix: String): Unit = {
  _prefix = prefix
  List[(String,Routes)]().foreach {
    case (p, router) => router.setPrefix(prefix + (if(prefix.endsWith("/")) "" else "/") + p)
  }
}

def prefix = _prefix

lazy val defaultPrefix = { if(Routes.prefix.endsWith("/")) "" else "/" }


// @LINE:6
private[this] lazy val controllers_Assets_at0_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/assets/"),DynamicPart("file", """.+""",false))))
private[this] lazy val controllers_Assets_at0_invoker = createInvoker(
controllers.Assets.at(fakeValue[String], fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]),"GET", """ Map static resources from the /public folder to the /assets URL path""", Routes.prefix + """toggle-service/assets/$file<.+>"""))
        

// @LINE:9
private[this] lazy val no_samordnaopptak_apidoc_controllers_ApiDocController_get1_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("api-docs"))))
private[this] lazy val no_samordnaopptak_apidoc_controllers_ApiDocController_get1_invoker = createInvoker(
no.samordnaopptak.apidoc.controllers.ApiDocController.get,
HandlerDef(this.getClass.getClassLoader, "", "no.samordnaopptak.apidoc.controllers.ApiDocController", "get", Nil,"GET", """ Swagger api json doc page""", Routes.prefix + """api-docs"""))
        

// @LINE:12
private[this] lazy val controllers_Application_index2_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service"))))
private[this] lazy val controllers_Application_index2_invoker = createInvoker(
controllers.Application.index,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Nil,"GET", """ Index page""", Routes.prefix + """toggle-service"""))
        

// @LINE:15
private[this] lazy val controllers_Application_changeLogLevel3_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/logLevel/"),DynamicPart("level", """(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)""",false))))
private[this] lazy val controllers_Application_changeLogLevel3_invoker = createInvoker(
controllers.Application.changeLogLevel(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "changeLogLevel", Seq(classOf[String]),"PUT", """ Change log level""", Routes.prefix + """toggle-service/logLevel/$level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>"""))
        

// @LINE:17
private[this] lazy val controllers_Application_changeLogLevelGet4_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/logLevel/"),DynamicPart("level", """(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)""",false))))
private[this] lazy val controllers_Application_changeLogLevelGet4_invoker = createInvoker(
controllers.Application.changeLogLevelGet(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "changeLogLevelGet", Seq(classOf[String]),"GET", """ Change log level convenience method""", Routes.prefix + """toggle-service/logLevel/$level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>"""))
        

// @LINE:20
private[this] lazy val controllers_Admin_ping5_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/admin/ping"))))
private[this] lazy val controllers_Admin_ping5_invoker = createInvoker(
controllers.Admin.ping,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Admin", "ping", Nil,"GET", """ Admin""", Routes.prefix + """toggle-service/admin/ping"""))
        

// @LINE:21
private[this] lazy val controllers_Admin_jvmstats6_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/admin/jvmstats"))))
private[this] lazy val controllers_Admin_jvmstats6_invoker = createInvoker(
controllers.Admin.jvmstats,
HandlerDef(this.getClass.getClassLoader, "", "controllers.Admin", "jvmstats", Nil,"GET", """""", Routes.prefix + """toggle-service/admin/jvmstats"""))
        

// @LINE:24
private[this] lazy val controllers_ToggleController_getToggles7_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles"))))
private[this] lazy val controllers_ToggleController_getToggles7_invoker = createInvoker(
controllers.ToggleController.getToggles(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getToggles", Seq(classOf[String]),"GET", """ Toggle""", Routes.prefix + """toggle-service/toggles"""))
        

// @LINE:25
private[this] lazy val controllers_ToggleController_updateAll8_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles"))))
private[this] lazy val controllers_ToggleController_updateAll8_invoker = createInvoker(
controllers.ToggleController.updateAll,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "updateAll", Nil,"POST", """""", Routes.prefix + """toggle-service/toggles"""))
        

// @LINE:26
private[this] lazy val controllers_ToggleController_createAll9_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles"))))
private[this] lazy val controllers_ToggleController_createAll9_invoker = createInvoker(
controllers.ToggleController.createAll,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "createAll", Nil,"PUT", """""", Routes.prefix + """toggle-service/toggles"""))
        

// @LINE:27
private[this] lazy val controllers_ToggleController_create10_route = Route("PUT", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false))))
private[this] lazy val controllers_ToggleController_create10_invoker = createInvoker(
controllers.ToggleController.create(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "create", Seq(classOf[String]),"PUT", """""", Routes.prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>"""))
        

// @LINE:28
private[this] lazy val controllers_ToggleController_update11_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false))))
private[this] lazy val controllers_ToggleController_update11_invoker = createInvoker(
controllers.ToggleController.update(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "update", Seq(classOf[String]),"POST", """""", Routes.prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>"""))
        

// @LINE:29
private[this] lazy val controllers_ToggleController_delete12_route = Route("DELETE", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false))))
private[this] lazy val controllers_ToggleController_delete12_invoker = createInvoker(
controllers.ToggleController.delete(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "delete", Seq(classOf[String]),"DELETE", """""", Routes.prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>"""))
        

// @LINE:30
private[this] lazy val controllers_ToggleController_getByName13_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false))))
private[this] lazy val controllers_ToggleController_getByName13_invoker = createInvoker(
controllers.ToggleController.getByName(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getByName", Seq(classOf[String]),"GET", """""", Routes.prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>"""))
        

// @LINE:31
private[this] lazy val controllers_ToggleController_copyFrom14_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/from/"),DynamicPart("host", """.+""",false))))
private[this] lazy val controllers_ToggleController_copyFrom14_invoker = createInvoker(
controllers.ToggleController.copyFrom(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "copyFrom", Seq(classOf[String]),"GET", """""", Routes.prefix + """toggle-service/toggles/from/$host<.+>"""))
        

// @LINE:32
private[this] lazy val controllers_ToggleController_deleteToggles15_route = Route("DELETE", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles"))))
private[this] lazy val controllers_ToggleController_deleteToggles15_invoker = createInvoker(
controllers.ToggleController.deleteToggles(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "deleteToggles", Seq(classOf[String]),"DELETE", """""", Routes.prefix + """toggle-service/toggles"""))
        

// @LINE:35
private[this] lazy val controllers_ToggleController_getAllHistory16_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/history"))))
private[this] lazy val controllers_ToggleController_getAllHistory16_invoker = createInvoker(
controllers.ToggleController.getAllHistory,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getAllHistory", Nil,"GET", """ Toggle History""", Routes.prefix + """toggle-service/history"""))
        

// @LINE:36
private[this] lazy val controllers_ToggleController_getHistory17_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false),StaticPart("/history"))))
private[this] lazy val controllers_ToggleController_getHistory17_invoker = createInvoker(
controllers.ToggleController.getHistory(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getHistory", Seq(classOf[String]),"GET", """""", Routes.prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>/history"""))
        

// @LINE:39
private[this] lazy val controllers_ToggleAdmin_login18_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/login"))))
private[this] lazy val controllers_ToggleAdmin_login18_invoker = createInvoker(
controllers.ToggleAdmin.login,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "login", Nil,"GET", """ Toggle Admin UI""", Routes.prefix + """toggle-service/ui/login"""))
        

// @LINE:40
private[this] lazy val controllers_ToggleAdmin_authenticate19_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/login"))))
private[this] lazy val controllers_ToggleAdmin_authenticate19_invoker = createInvoker(
controllers.ToggleAdmin.authenticate,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "authenticate", Nil,"POST", """""", Routes.prefix + """toggle-service/ui/login"""))
        

// @LINE:41
private[this] lazy val controllers_ToggleAdmin_logout20_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/logout"))))
private[this] lazy val controllers_ToggleAdmin_logout20_invoker = createInvoker(
controllers.ToggleAdmin.logout,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "logout", Nil,"GET", """""", Routes.prefix + """toggle-service/ui/logout"""))
        

// @LINE:43
private[this] lazy val controllers_ToggleAdmin_togglesList21_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/togglesList"))))
private[this] lazy val controllers_ToggleAdmin_togglesList21_invoker = createInvoker(
controllers.ToggleAdmin.togglesList,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "togglesList", Nil,"GET", """""", Routes.prefix + """toggle-service/ui/togglesList"""))
        

// @LINE:45
private[this] lazy val controllers_ToggleAdmin_editTogglesView22_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/editToggles"))))
private[this] lazy val controllers_ToggleAdmin_editTogglesView22_invoker = createInvoker(
controllers.ToggleAdmin.editTogglesView,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "editTogglesView", Nil,"GET", """""", Routes.prefix + """toggle-service/ui/editToggles"""))
        

// @LINE:46
private[this] lazy val controllers_ToggleAdmin_editToggles23_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/editToggles"))))
private[this] lazy val controllers_ToggleAdmin_editToggles23_invoker = createInvoker(
controllers.ToggleAdmin.editToggles,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "editToggles", Nil,"POST", """""", Routes.prefix + """toggle-service/ui/editToggles"""))
        

// @LINE:48
private[this] lazy val controllers_ToggleAdmin_getHistory24_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/ui/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false),StaticPart("/history"))))
private[this] lazy val controllers_ToggleAdmin_getHistory24_invoker = createInvoker(
controllers.ToggleAdmin.getHistory(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "getHistory", Seq(classOf[String]),"GET", """""", Routes.prefix + """toggle-service/ui/toggles/$toggleName<[a-zA-Z0-9_.%]+>/history"""))
        

// @LINE:51
private[this] lazy val controllers_ToggleUsagesController_register25_route = Route("POST", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/registrations"))))
private[this] lazy val controllers_ToggleUsagesController_register25_invoker = createInvoker(
controllers.ToggleUsagesController.register,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "register", Nil,"POST", """ Toggle Usages""", Routes.prefix + """toggle-service/registrations"""))
        

// @LINE:52
private[this] lazy val controllers_ToggleUsagesController_getAllUsages26_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/usages"))))
private[this] lazy val controllers_ToggleUsagesController_getAllUsages26_invoker = createInvoker(
controllers.ToggleUsagesController.getAllUsages,
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "getAllUsages", Nil,"GET", """""", Routes.prefix + """toggle-service/usages"""))
        

// @LINE:53
private[this] lazy val controllers_ToggleUsagesController_getToggleUsages27_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/toggles/"),DynamicPart("toggleName", """[a-zA-Z0-9_.%]+""",false),StaticPart("/usages"))))
private[this] lazy val controllers_ToggleUsagesController_getToggleUsages27_invoker = createInvoker(
controllers.ToggleUsagesController.getToggleUsages(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "getToggleUsages", Seq(classOf[String]),"GET", """""", Routes.prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>/usages"""))
        

// @LINE:54
private[this] lazy val controllers_ToggleUsagesController_getTogglesByUser28_route = Route("GET", PathPattern(List(StaticPart(Routes.prefix),StaticPart(Routes.defaultPrefix),StaticPart("toggle-service/usages/"),DynamicPart("user", """[a-zA-Z0-9_.\-%]+""",false))))
private[this] lazy val controllers_ToggleUsagesController_getTogglesByUser28_invoker = createInvoker(
controllers.ToggleUsagesController.getTogglesByUser(fakeValue[String]),
HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "getTogglesByUser", Seq(classOf[String]),"GET", """""", Routes.prefix + """toggle-service/usages/$user<[a-zA-Z0-9_.\-%]+>"""))
        
def documentation = List(("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/assets/$file<.+>""","""controllers.Assets.at(path:String = "/public", file:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """api-docs""","""no.samordnaopptak.apidoc.controllers.ApiDocController.get"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service""","""controllers.Application.index"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/logLevel/$level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>""","""controllers.Application.changeLogLevel(level:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/logLevel/$level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>""","""controllers.Application.changeLogLevelGet(level:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/admin/ping""","""controllers.Admin.ping"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/admin/jvmstats""","""controllers.Admin.jvmstats"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles""","""controllers.ToggleController.getToggles(filter:String ?= "")"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles""","""controllers.ToggleController.updateAll"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles""","""controllers.ToggleController.createAll"""),("""PUT""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""","""controllers.ToggleController.create(toggleName:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""","""controllers.ToggleController.update(toggleName:String)"""),("""DELETE""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""","""controllers.ToggleController.delete(toggleName:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""","""controllers.ToggleController.getByName(toggleName:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/from/$host<.+>""","""controllers.ToggleController.copyFrom(host:String)"""),("""DELETE""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles""","""controllers.ToggleController.deleteToggles(filter:String ?= "")"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/history""","""controllers.ToggleController.getAllHistory"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>/history""","""controllers.ToggleController.getHistory(toggleName:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/login""","""controllers.ToggleAdmin.login"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/login""","""controllers.ToggleAdmin.authenticate"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/logout""","""controllers.ToggleAdmin.logout"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/togglesList""","""controllers.ToggleAdmin.togglesList"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/editToggles""","""controllers.ToggleAdmin.editTogglesView"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/editToggles""","""controllers.ToggleAdmin.editToggles"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/ui/toggles/$toggleName<[a-zA-Z0-9_.%]+>/history""","""controllers.ToggleAdmin.getHistory(toggleName:String)"""),("""POST""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/registrations""","""controllers.ToggleUsagesController.register"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/usages""","""controllers.ToggleUsagesController.getAllUsages"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>/usages""","""controllers.ToggleUsagesController.getToggleUsages(toggleName:String)"""),("""GET""", prefix + (if(prefix.endsWith("/")) "" else "/") + """toggle-service/usages/$user<[a-zA-Z0-9_.\-%]+>""","""controllers.ToggleUsagesController.getTogglesByUser(user:String)""")).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
  case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
  case l => s ++ l.asInstanceOf[List[(String,String,String)]]
}}
      

def routes:PartialFunction[RequestHeader,Handler] = {

// @LINE:6
case controllers_Assets_at0_route(params) => {
   call(Param[String]("path", Right("/public")), params.fromPath[String]("file", None)) { (path, file) =>
        controllers_Assets_at0_invoker.call(controllers.Assets.at(path, file))
   }
}
        

// @LINE:9
case no_samordnaopptak_apidoc_controllers_ApiDocController_get1_route(params) => {
   call { 
        no_samordnaopptak_apidoc_controllers_ApiDocController_get1_invoker.call(no.samordnaopptak.apidoc.controllers.ApiDocController.get)
   }
}
        

// @LINE:12
case controllers_Application_index2_route(params) => {
   call { 
        controllers_Application_index2_invoker.call(controllers.Application.index)
   }
}
        

// @LINE:15
case controllers_Application_changeLogLevel3_route(params) => {
   call(params.fromPath[String]("level", None)) { (level) =>
        controllers_Application_changeLogLevel3_invoker.call(controllers.Application.changeLogLevel(level))
   }
}
        

// @LINE:17
case controllers_Application_changeLogLevelGet4_route(params) => {
   call(params.fromPath[String]("level", None)) { (level) =>
        controllers_Application_changeLogLevelGet4_invoker.call(controllers.Application.changeLogLevelGet(level))
   }
}
        

// @LINE:20
case controllers_Admin_ping5_route(params) => {
   call { 
        controllers_Admin_ping5_invoker.call(controllers.Admin.ping)
   }
}
        

// @LINE:21
case controllers_Admin_jvmstats6_route(params) => {
   call { 
        controllers_Admin_jvmstats6_invoker.call(controllers.Admin.jvmstats)
   }
}
        

// @LINE:24
case controllers_ToggleController_getToggles7_route(params) => {
   call(params.fromQuery[String]("filter", Some(""))) { (filter) =>
        controllers_ToggleController_getToggles7_invoker.call(controllers.ToggleController.getToggles(filter))
   }
}
        

// @LINE:25
case controllers_ToggleController_updateAll8_route(params) => {
   call { 
        controllers_ToggleController_updateAll8_invoker.call(controllers.ToggleController.updateAll)
   }
}
        

// @LINE:26
case controllers_ToggleController_createAll9_route(params) => {
   call { 
        controllers_ToggleController_createAll9_invoker.call(controllers.ToggleController.createAll)
   }
}
        

// @LINE:27
case controllers_ToggleController_create10_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleController_create10_invoker.call(controllers.ToggleController.create(toggleName))
   }
}
        

// @LINE:28
case controllers_ToggleController_update11_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleController_update11_invoker.call(controllers.ToggleController.update(toggleName))
   }
}
        

// @LINE:29
case controllers_ToggleController_delete12_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleController_delete12_invoker.call(controllers.ToggleController.delete(toggleName))
   }
}
        

// @LINE:30
case controllers_ToggleController_getByName13_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleController_getByName13_invoker.call(controllers.ToggleController.getByName(toggleName))
   }
}
        

// @LINE:31
case controllers_ToggleController_copyFrom14_route(params) => {
   call(params.fromPath[String]("host", None)) { (host) =>
        controllers_ToggleController_copyFrom14_invoker.call(controllers.ToggleController.copyFrom(host))
   }
}
        

// @LINE:32
case controllers_ToggleController_deleteToggles15_route(params) => {
   call(params.fromQuery[String]("filter", Some(""))) { (filter) =>
        controllers_ToggleController_deleteToggles15_invoker.call(controllers.ToggleController.deleteToggles(filter))
   }
}
        

// @LINE:35
case controllers_ToggleController_getAllHistory16_route(params) => {
   call { 
        controllers_ToggleController_getAllHistory16_invoker.call(controllers.ToggleController.getAllHistory)
   }
}
        

// @LINE:36
case controllers_ToggleController_getHistory17_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleController_getHistory17_invoker.call(controllers.ToggleController.getHistory(toggleName))
   }
}
        

// @LINE:39
case controllers_ToggleAdmin_login18_route(params) => {
   call { 
        controllers_ToggleAdmin_login18_invoker.call(controllers.ToggleAdmin.login)
   }
}
        

// @LINE:40
case controllers_ToggleAdmin_authenticate19_route(params) => {
   call { 
        controllers_ToggleAdmin_authenticate19_invoker.call(controllers.ToggleAdmin.authenticate)
   }
}
        

// @LINE:41
case controllers_ToggleAdmin_logout20_route(params) => {
   call { 
        controllers_ToggleAdmin_logout20_invoker.call(controllers.ToggleAdmin.logout)
   }
}
        

// @LINE:43
case controllers_ToggleAdmin_togglesList21_route(params) => {
   call { 
        controllers_ToggleAdmin_togglesList21_invoker.call(controllers.ToggleAdmin.togglesList)
   }
}
        

// @LINE:45
case controllers_ToggleAdmin_editTogglesView22_route(params) => {
   call { 
        controllers_ToggleAdmin_editTogglesView22_invoker.call(controllers.ToggleAdmin.editTogglesView)
   }
}
        

// @LINE:46
case controllers_ToggleAdmin_editToggles23_route(params) => {
   call { 
        controllers_ToggleAdmin_editToggles23_invoker.call(controllers.ToggleAdmin.editToggles)
   }
}
        

// @LINE:48
case controllers_ToggleAdmin_getHistory24_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleAdmin_getHistory24_invoker.call(controllers.ToggleAdmin.getHistory(toggleName))
   }
}
        

// @LINE:51
case controllers_ToggleUsagesController_register25_route(params) => {
   call { 
        controllers_ToggleUsagesController_register25_invoker.call(controllers.ToggleUsagesController.register)
   }
}
        

// @LINE:52
case controllers_ToggleUsagesController_getAllUsages26_route(params) => {
   call { 
        controllers_ToggleUsagesController_getAllUsages26_invoker.call(controllers.ToggleUsagesController.getAllUsages)
   }
}
        

// @LINE:53
case controllers_ToggleUsagesController_getToggleUsages27_route(params) => {
   call(params.fromPath[String]("toggleName", None)) { (toggleName) =>
        controllers_ToggleUsagesController_getToggleUsages27_invoker.call(controllers.ToggleUsagesController.getToggleUsages(toggleName))
   }
}
        

// @LINE:54
case controllers_ToggleUsagesController_getTogglesByUser28_route(params) => {
   call(params.fromPath[String]("user", None)) { (user) =>
        controllers_ToggleUsagesController_getTogglesByUser28_invoker.call(controllers.ToggleUsagesController.getTogglesByUser(user))
   }
}
        
}

}
     