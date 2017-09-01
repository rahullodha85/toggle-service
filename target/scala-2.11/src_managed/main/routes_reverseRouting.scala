// @SOURCE:/Users/461967/dev/toggle-service/conf/routes
// @HASH:4f034d09cfbc834baa9fa8d5910645b9063e9c79
// @DATE:Thu May 25 14:43:55 EDT 2017

import Routes.{prefix => _prefix, defaultPrefix => _defaultPrefix}
import play.core._
import play.core.Router._
import play.core.Router.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._
import _root_.controllers.Assets.Asset

import Router.queryString


// @LINE:54
// @LINE:53
// @LINE:52
// @LINE:51
// @LINE:48
// @LINE:46
// @LINE:45
// @LINE:43
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:36
// @LINE:35
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:15
// @LINE:12
// @LINE:6
package controllers {

// @LINE:6
class ReverseAssets {


// @LINE:6
def at(file:String): Call = {
   implicit val _rrc = new ReverseRouteContext(Map(("path", "/public")))
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/assets/" + implicitly[PathBindable[String]].unbind("file", file))
}
                        

}
                          

// @LINE:21
// @LINE:20
class ReverseAdmin {


// @LINE:20
def ping(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/admin/ping")
}
                        

// @LINE:21
def jvmstats(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/admin/jvmstats")
}
                        

}
                          

// @LINE:54
// @LINE:53
// @LINE:52
// @LINE:51
class ReverseToggleUsagesController {


// @LINE:53
def getToggleUsages(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName) + "/usages")
}
                        

// @LINE:52
def getAllUsages(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/usages")
}
                        

// @LINE:51
def register(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "toggle-service/registrations")
}
                        

// @LINE:54
def getTogglesByUser(user:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/usages/" + implicitly[PathBindable[String]].unbind("user", user))
}
                        

}
                          

// @LINE:36
// @LINE:35
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
class ReverseToggleController {


// @LINE:31
def copyFrom(host:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/toggles/from/" + implicitly[PathBindable[String]].unbind("host", host))
}
                        

// @LINE:24
def getToggles(filter:String = ""): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/toggles" + queryString(List(if(filter == "") None else Some(implicitly[QueryStringBindable[String]].unbind("filter", filter)))))
}
                        

// @LINE:25
def updateAll(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "toggle-service/toggles")
}
                        

// @LINE:26
def createAll(): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "toggle-service/toggles")
}
                        

// @LINE:36
def getHistory(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName) + "/history")
}
                        

// @LINE:35
def getAllHistory(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/history")
}
                        

// @LINE:32
def deleteToggles(filter:String = ""): Call = {
   import ReverseRouteContext.empty
   Call("DELETE", _prefix + { _defaultPrefix } + "toggle-service/toggles" + queryString(List(if(filter == "") None else Some(implicitly[QueryStringBindable[String]].unbind("filter", filter)))))
}
                        

// @LINE:27
def create(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "toggle-service/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName))
}
                        

// @LINE:29
def delete(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("DELETE", _prefix + { _defaultPrefix } + "toggle-service/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName))
}
                        

// @LINE:30
def getByName(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName))
}
                        

// @LINE:28
def update(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "toggle-service/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName))
}
                        

}
                          

// @LINE:17
// @LINE:15
// @LINE:12
class ReverseApplication {


// @LINE:17
def changeLogLevelGet(level:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/logLevel/" + implicitly[PathBindable[String]].unbind("level", level))
}
                        

// @LINE:15
def changeLogLevel(level:String): Call = {
   import ReverseRouteContext.empty
   Call("PUT", _prefix + { _defaultPrefix } + "toggle-service/logLevel/" + implicitly[PathBindable[String]].unbind("level", level))
}
                        

// @LINE:12
def index(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service")
}
                        

}
                          

// @LINE:48
// @LINE:46
// @LINE:45
// @LINE:43
// @LINE:41
// @LINE:40
// @LINE:39
class ReverseToggleAdmin {


// @LINE:45
def editTogglesView(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/ui/editToggles")
}
                        

// @LINE:48
def getHistory(toggleName:String): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/ui/toggles/" + implicitly[PathBindable[String]].unbind("toggleName", toggleName) + "/history")
}
                        

// @LINE:43
def togglesList(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/ui/togglesList")
}
                        

// @LINE:41
def logout(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/ui/logout")
}
                        

// @LINE:46
def editToggles(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "toggle-service/ui/editToggles")
}
                        

// @LINE:40
def authenticate(): Call = {
   import ReverseRouteContext.empty
   Call("POST", _prefix + { _defaultPrefix } + "toggle-service/ui/login")
}
                        

// @LINE:39
def login(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "toggle-service/ui/login")
}
                        

}
                          
}
                  

// @LINE:9
package no.samordnaopptak.apidoc.controllers {

// @LINE:9
class ReverseApiDocController {


// @LINE:9
def get(): Call = {
   import ReverseRouteContext.empty
   Call("GET", _prefix + { _defaultPrefix } + "api-docs")
}
                        

}
                          
}
                  


// @LINE:54
// @LINE:53
// @LINE:52
// @LINE:51
// @LINE:48
// @LINE:46
// @LINE:45
// @LINE:43
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:36
// @LINE:35
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:15
// @LINE:12
// @LINE:6
package controllers.javascript {
import ReverseRouteContext.empty

// @LINE:6
class ReverseAssets {


// @LINE:6
def at : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Assets.at",
   """
      function(file) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
      }
   """
)
                        

}
              

// @LINE:21
// @LINE:20
class ReverseAdmin {


// @LINE:20
def ping : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Admin.ping",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/admin/ping"})
      }
   """
)
                        

// @LINE:21
def jvmstats : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Admin.jvmstats",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/admin/jvmstats"})
      }
   """
)
                        

}
              

// @LINE:54
// @LINE:53
// @LINE:52
// @LINE:51
class ReverseToggleUsagesController {


// @LINE:53
def getToggleUsages : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleUsagesController.getToggleUsages",
   """
      function(toggleName) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName) + "/usages"})
      }
   """
)
                        

// @LINE:52
def getAllUsages : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleUsagesController.getAllUsages",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/usages"})
      }
   """
)
                        

// @LINE:51
def register : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleUsagesController.register",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/registrations"})
      }
   """
)
                        

// @LINE:54
def getTogglesByUser : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleUsagesController.getTogglesByUser",
   """
      function(user) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/usages/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("user", user)})
      }
   """
)
                        

}
              

// @LINE:36
// @LINE:35
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
class ReverseToggleController {


// @LINE:31
def copyFrom : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.copyFrom",
   """
      function(host) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/from/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("host", host)})
      }
   """
)
                        

// @LINE:24
def getToggles : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.getToggles",
   """
      function(filter) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles" + _qS([(filter == null ? null : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("filter", filter))])})
      }
   """
)
                        

// @LINE:25
def updateAll : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.updateAll",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles"})
      }
   """
)
                        

// @LINE:26
def createAll : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.createAll",
   """
      function() {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles"})
      }
   """
)
                        

// @LINE:36
def getHistory : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.getHistory",
   """
      function(toggleName) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName) + "/history"})
      }
   """
)
                        

// @LINE:35
def getAllHistory : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.getAllHistory",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/history"})
      }
   """
)
                        

// @LINE:32
def deleteToggles : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.deleteToggles",
   """
      function(filter) {
      return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles" + _qS([(filter == null ? null : (""" + implicitly[QueryStringBindable[String]].javascriptUnbind + """)("filter", filter))])})
      }
   """
)
                        

// @LINE:27
def create : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.create",
   """
      function(toggleName) {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName)})
      }
   """
)
                        

// @LINE:29
def delete : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.delete",
   """
      function(toggleName) {
      return _wA({method:"DELETE", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName)})
      }
   """
)
                        

// @LINE:30
def getByName : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.getByName",
   """
      function(toggleName) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName)})
      }
   """
)
                        

// @LINE:28
def update : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleController.update",
   """
      function(toggleName) {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName)})
      }
   """
)
                        

}
              

// @LINE:17
// @LINE:15
// @LINE:12
class ReverseApplication {


// @LINE:17
def changeLogLevelGet : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.changeLogLevelGet",
   """
      function(level) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/logLevel/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("level", level)})
      }
   """
)
                        

// @LINE:15
def changeLogLevel : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.changeLogLevel",
   """
      function(level) {
      return _wA({method:"PUT", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/logLevel/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("level", level)})
      }
   """
)
                        

// @LINE:12
def index : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.Application.index",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service"})
      }
   """
)
                        

}
              

// @LINE:48
// @LINE:46
// @LINE:45
// @LINE:43
// @LINE:41
// @LINE:40
// @LINE:39
class ReverseToggleAdmin {


// @LINE:45
def editTogglesView : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.editTogglesView",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/editToggles"})
      }
   """
)
                        

// @LINE:48
def getHistory : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.getHistory",
   """
      function(toggleName) {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/toggles/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("toggleName", toggleName) + "/history"})
      }
   """
)
                        

// @LINE:43
def togglesList : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.togglesList",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/togglesList"})
      }
   """
)
                        

// @LINE:41
def logout : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.logout",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/logout"})
      }
   """
)
                        

// @LINE:46
def editToggles : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.editToggles",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/editToggles"})
      }
   """
)
                        

// @LINE:40
def authenticate : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.authenticate",
   """
      function() {
      return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/login"})
      }
   """
)
                        

// @LINE:39
def login : JavascriptReverseRoute = JavascriptReverseRoute(
   "controllers.ToggleAdmin.login",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "toggle-service/ui/login"})
      }
   """
)
                        

}
              
}
        

// @LINE:9
package no.samordnaopptak.apidoc.controllers.javascript {
import ReverseRouteContext.empty

// @LINE:9
class ReverseApiDocController {


// @LINE:9
def get : JavascriptReverseRoute = JavascriptReverseRoute(
   "no.samordnaopptak.apidoc.controllers.ApiDocController.get",
   """
      function() {
      return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "api-docs"})
      }
   """
)
                        

}
              
}
        


// @LINE:54
// @LINE:53
// @LINE:52
// @LINE:51
// @LINE:48
// @LINE:46
// @LINE:45
// @LINE:43
// @LINE:41
// @LINE:40
// @LINE:39
// @LINE:36
// @LINE:35
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
// @LINE:21
// @LINE:20
// @LINE:17
// @LINE:15
// @LINE:12
// @LINE:6
package controllers.ref {


// @LINE:6
class ReverseAssets {


// @LINE:6
def at(path:String, file:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Assets.at(path, file), HandlerDef(this.getClass.getClassLoader, "", "controllers.Assets", "at", Seq(classOf[String], classOf[String]), "GET", """ Map static resources from the /public folder to the /assets URL path""", _prefix + """toggle-service/assets/$file<.+>""")
)
                      

}
                          

// @LINE:21
// @LINE:20
class ReverseAdmin {


// @LINE:20
def ping(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Admin.ping(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Admin", "ping", Seq(), "GET", """ Admin""", _prefix + """toggle-service/admin/ping""")
)
                      

// @LINE:21
def jvmstats(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Admin.jvmstats(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Admin", "jvmstats", Seq(), "GET", """""", _prefix + """toggle-service/admin/jvmstats""")
)
                      

}
                          

// @LINE:54
// @LINE:53
// @LINE:52
// @LINE:51
class ReverseToggleUsagesController {


// @LINE:53
def getToggleUsages(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleUsagesController.getToggleUsages(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "getToggleUsages", Seq(classOf[String]), "GET", """""", _prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>/usages""")
)
                      

// @LINE:52
def getAllUsages(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleUsagesController.getAllUsages(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "getAllUsages", Seq(), "GET", """""", _prefix + """toggle-service/usages""")
)
                      

// @LINE:51
def register(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleUsagesController.register(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "register", Seq(), "POST", """ Toggle Usages""", _prefix + """toggle-service/registrations""")
)
                      

// @LINE:54
def getTogglesByUser(user:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleUsagesController.getTogglesByUser(user), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleUsagesController", "getTogglesByUser", Seq(classOf[String]), "GET", """""", _prefix + """toggle-service/usages/$user<[a-zA-Z0-9_.\-%]+>""")
)
                      

}
                          

// @LINE:36
// @LINE:35
// @LINE:32
// @LINE:31
// @LINE:30
// @LINE:29
// @LINE:28
// @LINE:27
// @LINE:26
// @LINE:25
// @LINE:24
class ReverseToggleController {


// @LINE:31
def copyFrom(host:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.copyFrom(host), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "copyFrom", Seq(classOf[String]), "GET", """""", _prefix + """toggle-service/toggles/from/$host<.+>""")
)
                      

// @LINE:24
def getToggles(filter:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.getToggles(filter), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getToggles", Seq(classOf[String]), "GET", """ Toggle""", _prefix + """toggle-service/toggles""")
)
                      

// @LINE:25
def updateAll(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.updateAll(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "updateAll", Seq(), "POST", """""", _prefix + """toggle-service/toggles""")
)
                      

// @LINE:26
def createAll(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.createAll(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "createAll", Seq(), "PUT", """""", _prefix + """toggle-service/toggles""")
)
                      

// @LINE:36
def getHistory(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.getHistory(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getHistory", Seq(classOf[String]), "GET", """""", _prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>/history""")
)
                      

// @LINE:35
def getAllHistory(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.getAllHistory(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getAllHistory", Seq(), "GET", """ Toggle History""", _prefix + """toggle-service/history""")
)
                      

// @LINE:32
def deleteToggles(filter:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.deleteToggles(filter), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "deleteToggles", Seq(classOf[String]), "DELETE", """""", _prefix + """toggle-service/toggles""")
)
                      

// @LINE:27
def create(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.create(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "create", Seq(classOf[String]), "PUT", """""", _prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""")
)
                      

// @LINE:29
def delete(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.delete(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "delete", Seq(classOf[String]), "DELETE", """""", _prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""")
)
                      

// @LINE:30
def getByName(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.getByName(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "getByName", Seq(classOf[String]), "GET", """""", _prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""")
)
                      

// @LINE:28
def update(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleController.update(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleController", "update", Seq(classOf[String]), "POST", """""", _prefix + """toggle-service/toggles/$toggleName<[a-zA-Z0-9_.%]+>""")
)
                      

}
                          

// @LINE:17
// @LINE:15
// @LINE:12
class ReverseApplication {


// @LINE:17
def changeLogLevelGet(level:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.changeLogLevelGet(level), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "changeLogLevelGet", Seq(classOf[String]), "GET", """ Change log level convenience method""", _prefix + """toggle-service/logLevel/$level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>""")
)
                      

// @LINE:15
def changeLogLevel(level:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.changeLogLevel(level), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "changeLogLevel", Seq(classOf[String]), "PUT", """ Change log level""", _prefix + """toggle-service/logLevel/$level<(ALL|TRACE|DEBUG|INFO|WARN|ERROR|OFF)>""")
)
                      

// @LINE:12
def index(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.Application.index(), HandlerDef(this.getClass.getClassLoader, "", "controllers.Application", "index", Seq(), "GET", """ Index page""", _prefix + """toggle-service""")
)
                      

}
                          

// @LINE:48
// @LINE:46
// @LINE:45
// @LINE:43
// @LINE:41
// @LINE:40
// @LINE:39
class ReverseToggleAdmin {


// @LINE:45
def editTogglesView(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.editTogglesView(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "editTogglesView", Seq(), "GET", """""", _prefix + """toggle-service/ui/editToggles""")
)
                      

// @LINE:48
def getHistory(toggleName:String): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.getHistory(toggleName), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "getHistory", Seq(classOf[String]), "GET", """""", _prefix + """toggle-service/ui/toggles/$toggleName<[a-zA-Z0-9_.%]+>/history""")
)
                      

// @LINE:43
def togglesList(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.togglesList(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "togglesList", Seq(), "GET", """""", _prefix + """toggle-service/ui/togglesList""")
)
                      

// @LINE:41
def logout(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.logout(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "logout", Seq(), "GET", """""", _prefix + """toggle-service/ui/logout""")
)
                      

// @LINE:46
def editToggles(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.editToggles(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "editToggles", Seq(), "POST", """""", _prefix + """toggle-service/ui/editToggles""")
)
                      

// @LINE:40
def authenticate(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.authenticate(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "authenticate", Seq(), "POST", """""", _prefix + """toggle-service/ui/login""")
)
                      

// @LINE:39
def login(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   controllers.ToggleAdmin.login(), HandlerDef(this.getClass.getClassLoader, "", "controllers.ToggleAdmin", "login", Seq(), "GET", """ Toggle Admin UI""", _prefix + """toggle-service/ui/login""")
)
                      

}
                          
}
        

// @LINE:9
package no.samordnaopptak.apidoc.controllers.ref {


// @LINE:9
class ReverseApiDocController {


// @LINE:9
def get(): play.api.mvc.HandlerRef[_] = new play.api.mvc.HandlerRef(
   no.samordnaopptak.apidoc.controllers.ApiDocController.get(), HandlerDef(this.getClass.getClassLoader, "", "no.samordnaopptak.apidoc.controllers.ApiDocController", "get", Seq(), "GET", """ Swagger api json doc page""", _prefix + """api-docs""")
)
                      

}
                          
}
        
    