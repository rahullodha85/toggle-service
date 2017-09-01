
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
object header extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(title: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.17*/("""
"""),format.raw/*2.1*/("""<div class="header">
    <div class="inner">
        <h1>
            <a href=""""),_display_(/*5.23*/{routes.ToggleAdmin.togglesList}),format.raw/*5.55*/("""">
                <img src=""""),_display_(/*6.28*/routes/*6.34*/.Assets.at("images/hbc-icon.png")),format.raw/*6.67*/("""" border="0">
            </a>&nbsp;"""),_display_(/*7.24*/title),format.raw/*7.29*/("""
        """),format.raw/*8.9*/("""</h1>
        <ul>
            <li><a class="new-button">Add</a></li>
            <li><a href=""""),_display_(/*11.27*/{routes.ToggleAdmin.togglesList}),format.raw/*11.59*/("""" class="new-button">Toggle List</a></li>
            <li><a class="logoff-button">Log Off</a></li>
        </ul>
    </div>
</div>
"""))}
  }

  def render(title:String): play.twirl.api.HtmlFormat.Appendable = apply(title)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (title) => apply(title)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Thu May 25 14:43:55 EDT 2017
                  SOURCE: /Users/461967/dev/toggle-service/app/views/header.scala.html
                  HASH: e9178fb840a18cc0c79acf2d4ab4080ef72bc546
                  MATRIX: 506->1|609->16|636->17|742->97|794->129|850->159|864->165|917->198|980->235|1005->240|1040->249|1163->345|1216->377
                  LINES: 19->1|22->1|23->2|26->5|26->5|27->6|27->6|27->6|28->7|28->7|29->8|32->11|32->11
                  -- GENERATED --
              */
          