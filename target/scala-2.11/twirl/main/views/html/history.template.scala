
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
object history extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(toggleName : String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.23*/("""
"""),format.raw/*2.1*/("""<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

        <title>History</title>
        <link rel="stylesheet" type="text/css" href=""""),_display_(/*8.55*/routes/*8.61*/.Assets.at("css/feature_toggles.css")),format.raw/*8.98*/("""">
        <link rel="stylesheet" type="text/css" href=""""),_display_(/*9.55*/routes/*9.61*/.Assets.at("css/smoothness.css")),format.raw/*9.93*/("""">
        <meta name="viewport" content="width= device-width, initial-scale=1.0, maximum-scale=1.0, user-scale=0">

        <script type="text/javascript" src=""""),_display_(/*12.46*/routes/*12.52*/.Assets.at("js/underscore-min.js")),format.raw/*12.86*/(""""></script>
        <script type="text/javascript" src=""""),_display_(/*13.46*/routes/*13.52*/.Assets.at("js/jquery.min.js")),format.raw/*13.82*/(""""></script>
        <script language="JavaScript">

        $(document).ready(function()"""),format.raw/*16.37*/("""{"""),format.raw/*16.38*/("""
            """),format.raw/*17.13*/("""$.ajax("""),format.raw/*17.20*/("""{"""),format.raw/*17.21*/("""
              """),format.raw/*18.15*/("""type : "GET",
              url: """"),_display_(/*19.22*/{routes.ToggleController.getHistory(toggleName)}),format.raw/*19.70*/("""",
              success: function(data) """),format.raw/*20.39*/("""{"""),format.raw/*20.40*/("""
                """),format.raw/*21.17*/("""var toggleHistory = """),format.raw/*21.37*/("""{"""),format.raw/*21.38*/(""""toggleHistory" : data.response.results"""),format.raw/*21.77*/("""}"""),format.raw/*21.78*/(""",
                     templateString = $('#history-template').html(),
                     compiled = _.template(templateString);

                $('#tableHeader').after(
                    compiled(toggleHistory)
                );
              """),format.raw/*28.15*/("""}"""),format.raw/*28.16*/("""
            """),format.raw/*29.13*/("""}"""),format.raw/*29.14*/(""").fail(function(jqXHR, textStatus, errorThrown)"""),format.raw/*29.61*/("""{"""),format.raw/*29.62*/("""
                """),format.raw/*30.17*/("""if(jqXHR.status == 404) """),format.raw/*30.41*/("""{"""),format.raw/*30.42*/("""
                    """),format.raw/*31.21*/("""var noHistory = """),format.raw/*31.37*/("""{"""),format.raw/*31.38*/(""""toggleName" : """"),_display_(/*31.55*/toggleName),format.raw/*31.65*/("""""""),format.raw/*31.66*/("""}"""),format.raw/*31.67*/(""",
                     templateString = $('#no-history-template').html(),
                     compiled = _.template(templateString);

                    $('#tableHeader').after(
                        compiled(noHistory)
                    );
                """),format.raw/*38.17*/("""}"""),format.raw/*38.18*/(""" """),format.raw/*38.19*/("""else """),format.raw/*38.24*/("""{"""),format.raw/*38.25*/("""
                   """),format.raw/*39.20*/("""alert("Unable to retrieve the feature toggle name: """),_display_(/*39.72*/toggleName),format.raw/*39.82*/("""");
                """),format.raw/*40.17*/("""}"""),format.raw/*40.18*/("""
            """),format.raw/*41.13*/("""}"""),format.raw/*41.14*/(""");
        """),format.raw/*42.9*/("""}"""),format.raw/*42.10*/(""")
    </script>
    <script id="history-template" type="text/x-handlebars-template">
        <% _(toggleHistory).each(function(item) """),format.raw/*45.49*/("""{"""),format.raw/*45.50*/(""" """),format.raw/*45.51*/("""%>
            <tr class="feature-row">
                <td bgcolor="#ffffff" align="left"> <%= item.modified_by %> </td>
                <td bgcolor="#ffffff" align="left"> <%= item.action %> </td>
                <td bgcolor="#ffffff" align="left"> <%= item.toggle_name %> </td>
                <td bgcolor="#ffffff" align="left"> <%= item.toggle_state ? "On" : "Off" %> </td>
                <td bgcolor="#ffffff" align="left" style="white-space:nowrap;"> <%= item.modified_timestamp %> </td>
            </tr>
        <% """),format.raw/*53.12*/("""}"""),format.raw/*53.13*/(""") %>
    </script>
    <script id="no-history-template" type="text/x-handlebars-template">
        <tr class="feature-row">
            <td bgcolor="#ffffff" align="left" colspan="5"> Feature toggle <%= toggleName %> has no history</td>
        </tr>
    </script>

    </head>
    <body>

    """),_display_(/*64.6*/header("Toggle History")),format.raw/*64.30*/("""

    """),format.raw/*66.5*/("""<br>
    <br>

    <div class="content">

        <table cellpadding="10px" cellspacing="0px" border="0" >
            <tbody>
            <tr id="tableHeader" style="white-space:nowrap;">
                <td bgcolor="#000000" style="color:#ffffff; font-weight:bold;" align="center">Modified By</td>
                <td bgcolor="#000000" style="color:#ffffff; font-weight:bold;" align="center">Action</td>
                <td bgcolor="#000000" style="color:#ffffff; font-weight:bold;" align="center">Feature Toggle</td>
                <td bgcolor="#000000" style="color:#ffffff; font-weight:bold;" align="center">State</td>
                <td bgcolor="#000000" style="color:#ffffff; font-weight:bold;" align="center">Timestamp</td>
            </tr>
            </tbody>
        </table>
    </div>

    <br>

    </body>
</html>
"""))}
  }

  def render(toggleName:String): play.twirl.api.HtmlFormat.Appendable = apply(toggleName)

  def f:((String) => play.twirl.api.HtmlFormat.Appendable) = (toggleName) => apply(toggleName)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Thu May 25 14:43:55 EDT 2017
                  SOURCE: /Users/461967/dev/toggle-service/app/views/history.scala.html
                  HASH: 59c4d2b23f7067df832e37c6fc2670c5c473a0e9
                  MATRIX: 507->1|616->22|643->23|872->226|886->232|943->269|1026->326|1040->332|1092->364|1281->526|1296->532|1351->566|1435->623|1450->629|1501->659|1617->747|1646->748|1687->761|1722->768|1751->769|1794->784|1856->819|1925->867|1994->908|2023->909|2068->926|2116->946|2145->947|2212->986|2241->987|2519->1237|2548->1238|2589->1251|2618->1252|2693->1299|2722->1300|2767->1317|2819->1341|2848->1342|2897->1363|2941->1379|2970->1380|3014->1397|3045->1407|3074->1408|3103->1409|3394->1672|3423->1673|3452->1674|3485->1679|3514->1680|3562->1700|3641->1752|3672->1762|3720->1782|3749->1783|3790->1796|3819->1797|3857->1808|3886->1809|4047->1942|4076->1943|4105->1944|4658->2469|4687->2470|5008->2765|5053->2789|5086->2795
                  LINES: 19->1|22->1|23->2|29->8|29->8|29->8|30->9|30->9|30->9|33->12|33->12|33->12|34->13|34->13|34->13|37->16|37->16|38->17|38->17|38->17|39->18|40->19|40->19|41->20|41->20|42->21|42->21|42->21|42->21|42->21|49->28|49->28|50->29|50->29|50->29|50->29|51->30|51->30|51->30|52->31|52->31|52->31|52->31|52->31|52->31|52->31|59->38|59->38|59->38|59->38|59->38|60->39|60->39|60->39|61->40|61->40|62->41|62->41|63->42|63->42|66->45|66->45|66->45|74->53|74->53|85->64|85->64|87->66
                  -- GENERATED --
              */
          