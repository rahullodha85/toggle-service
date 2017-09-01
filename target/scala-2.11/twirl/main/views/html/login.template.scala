
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._

import play.api.templates.PlayMagic._
import models._
import controllers._
import play.api.i18n._
import play.api.mvc._
import play.api.data._
import views.html._

/**/
object login extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[Form[Option[HbcUser]],Flash,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(form: Form[Option[HbcUser]])(implicit flash: Flash):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.54*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
    <head><meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

        <title>Login</title>
        <script type="text/javascript" src=""""),_display_(/*8.46*/routes/*8.52*/.Assets.at("js/jquery.min.js")),format.raw/*8.82*/(""""></script>
	    <script type="text/javascript" src=""""),_display_(/*9.43*/routes/*9.49*/.Assets.at("js/jquery-ui.min.js")),format.raw/*9.82*/(""""></script>
        <link rel="stylesheet" type="text/css" href=""""),_display_(/*10.55*/routes/*10.61*/.Assets.at("css/feature_toggles.css")),format.raw/*10.98*/("""">
        <link rel="stylesheet" type="text/css" href=""""),_display_(/*11.55*/routes/*11.61*/.Assets.at("css/smoothness.css")),format.raw/*11.93*/("""">
        <meta name="viewport" content="width= device-width, initial-scale=1.0, maximum-scale=1.0, user-scale=0">
    </head>
<body>

"""),_display_(/*16.2*/helper/*16.8*/.form(routes.ToggleAdmin.authenticate)/*16.46*/ {_display_(Seq[Any](format.raw/*16.48*/("""

	"""),format.raw/*18.2*/("""<div class="header">
		<div class="inner">
			<h1><img src=""""),_display_(/*20.19*/routes/*20.25*/.Assets.at("images/hbc-icon.png")),format.raw/*20.58*/("""" border="0">&nbsp;Toggles</h1>
		</div>
	</div>
	
	<div class="content">
		<div class="inner">
			<h1 class="title">Login</h1>

            """),_display_(/*28.14*/form/*28.18*/.globalError.map/*28.34*/ { error =>_display_(Seq[Any](format.raw/*28.45*/("""
            """),format.raw/*29.13*/("""<div class="inner" style="color: red">
                <strong>Error!</strong> """),_display_(/*30.42*/error/*30.47*/.message),format.raw/*30.55*/(""".
            </div>
            """)))}),format.raw/*32.14*/("""

            """),format.raw/*34.13*/("""<div class="inner">
				<label>Username</label>
				<font face="courier new" size="3"><tt>&nbsp;</tt></font>
				<input name="username" type="text">
            </div>

            <div class="inner">
				<label>Password</label>
				<font face="courier new" size="3"><tt>&nbsp;</tt></font>
				<input name="password" type="password">
				<br>
            </div>

            <div class="inner">
                <input type="submit" value="Login" name="submit">
            </div>
		</div>
	</div>
	<script>
		(function($)"""),format.raw/*53.15*/("""{"""),format.raw/*53.16*/("""
			"""),format.raw/*54.4*/("""$(document).ready(function()"""),format.raw/*54.32*/("""{"""),format.raw/*54.33*/("""
				"""),format.raw/*55.5*/("""$('form').each(function()"""),format.raw/*55.30*/("""{"""),format.raw/*55.31*/("""
					"""),format.raw/*56.6*/("""this.reset();
				"""),format.raw/*57.5*/("""}"""),format.raw/*57.6*/(""")
			"""),format.raw/*58.4*/("""}"""),format.raw/*58.5*/(""");
		"""),format.raw/*59.3*/("""}"""),format.raw/*59.4*/("""(jQuery));
	</script>

""")))}),format.raw/*62.2*/("""

"""),format.raw/*64.1*/("""</body>
</html>
"""))}
  }

  def render(form:Form[Option[HbcUser]],flash:Flash): play.twirl.api.HtmlFormat.Appendable = apply(form)(flash)

  def f:((Form[Option[HbcUser]]) => (Flash) => play.twirl.api.HtmlFormat.Appendable) = (form) => (flash) => apply(form)(flash)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Thu May 25 14:43:55 EDT 2017
                  SOURCE: /Users/461967/dev/toggle-service/app/views/login.scala.html
                  HASH: 6f8cc63c98f829046051d9c659ab1153e9b9d11e
                  MATRIX: 526->1|666->53|694->55|903->238|917->244|967->274|1047->328|1061->334|1114->367|1207->433|1222->439|1280->476|1364->533|1379->539|1432->571|1595->708|1609->714|1656->752|1696->754|1726->757|1814->818|1829->824|1883->857|2052->999|2065->1003|2090->1019|2139->1030|2180->1043|2287->1123|2301->1128|2330->1136|2395->1170|2437->1184|2986->1705|3015->1706|3046->1710|3102->1738|3131->1739|3163->1744|3216->1769|3245->1770|3278->1776|3323->1794|3351->1795|3383->1800|3411->1801|3443->1806|3471->1807|3525->1831|3554->1833
                  LINES: 19->1|22->1|24->3|29->8|29->8|29->8|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|37->16|37->16|37->16|37->16|39->18|41->20|41->20|41->20|49->28|49->28|49->28|49->28|50->29|51->30|51->30|51->30|53->32|55->34|74->53|74->53|75->54|75->54|75->54|76->55|76->55|76->55|77->56|78->57|78->57|79->58|79->58|80->59|80->59|83->62|85->64
                  -- GENERATED --
              */
          