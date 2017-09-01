
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
object add_edit_toggle extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template2[HbcUser,String,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(hbcUser: HbcUser, queryParamSafeName: String):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.48*/("""
"""),format.raw/*2.1*/("""<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">

        <title>Edit Toggle</title>
        <script type="text/javascript" src=""""),_display_(/*7.46*/routes/*7.52*/.Assets.at("js/jquery.min.js")),format.raw/*7.82*/(""""></script>
        <link rel="stylesheet" type="text/css" href=""""),_display_(/*8.55*/routes/*8.61*/.Assets.at("css/feature_toggles.css")),format.raw/*8.98*/("""">
        <link rel="stylesheet" type="text/css" href=""""),_display_(/*9.55*/routes/*9.61*/.Assets.at("css/smoothness.css")),format.raw/*9.93*/("""">
        <meta name="viewport" content="width= device-width, initial-scale=1.0, maximum-scale=1.0, user-scale=0">

        <script type="text/javascript">
            var toggleInformation,
                userName = """"),_display_(/*14.30*/{hbcUser.username}),format.raw/*14.48*/("""";

            $(function()"""),format.raw/*16.25*/("""{"""),format.raw/*16.26*/("""

                """),format.raw/*18.17*/("""$.ajax("""),format.raw/*18.24*/("""{"""),format.raw/*18.25*/("""
                    """),format.raw/*19.21*/("""type: "GET",
                    url: """"),_display_(/*20.28*/{routes.ToggleController.getByName(queryParamSafeName)}),format.raw/*20.83*/("""",
                    // The key needs to match your method's input parameter (case-sensitive).
                    success: function(data) """),format.raw/*22.45*/("""{"""),format.raw/*22.46*/("""
                      """),format.raw/*23.23*/("""toggleInformation = data.response.results
                      $('#feature_name').html(toggleInformation.toggle_name)
                      $('#type option[value="' + toggleInformation.toggle_type + '"]').prop('selected',true)
                      $('#desc').val(toggleInformation.description)
                      $('#editTable').show();
                    """),format.raw/*28.21*/("""}"""),format.raw/*28.22*/(""",
                    error: function(jqXHR, textStatus, errThrown) """),format.raw/*29.67*/("""{"""),format.raw/*29.68*/("""
                        """),format.raw/*30.25*/("""var err = jqXHR.responseJSON.errors[0];
                        var data = err.data || "";
                        var error = err.error || "";
                        var errMsg = "Failed to update toggle. " + data + ", " + error;
                        handleError(errMsg);
                    """),format.raw/*35.21*/("""}"""),format.raw/*35.22*/("""
                """),format.raw/*36.17*/("""}"""),format.raw/*36.18*/(""");
            """),format.raw/*37.13*/("""}"""),format.raw/*37.14*/(""")

            $('.logoff-button').click(function(event)"""),format.raw/*39.54*/("""{"""),format.raw/*39.55*/("""
                """),format.raw/*40.17*/("""event.preventDefault();
                var confirmation = confirm('Are you sure you want log off?');
                if (confirmation) """),format.raw/*42.35*/("""{"""),format.raw/*42.36*/("""
                    """),format.raw/*43.21*/("""window.location = '"""),_display_(/*43.41*/{routes.ToggleAdmin.logout}),format.raw/*43.68*/("""';
                """),format.raw/*44.17*/("""}"""),format.raw/*44.18*/("""
            """),format.raw/*45.13*/("""}"""),format.raw/*45.14*/(""");

            function saveToggle() """),format.raw/*47.35*/("""{"""),format.raw/*47.36*/("""
                """),format.raw/*48.17*/("""var item = """),format.raw/*48.28*/("""{"""),format.raw/*48.29*/(""""item": """),format.raw/*48.37*/("""{"""),format.raw/*48.38*/("""
                                """),format.raw/*49.33*/(""""toggle_name" : toggleInformation.toggle_name,
                                "toggle_state" : toggleInformation.toggle_state,
                                "created_timestamp" : "",
                                "modified_timestamp" : "",
                                "description" : $('#desc').val(),
                                "toggle_type" : $('#type').val(),
                                "modified_by" : userName
                           """),format.raw/*56.28*/("""}"""),format.raw/*56.29*/("""
                """),format.raw/*57.17*/("""}"""),format.raw/*57.18*/(""";
                function handleError(errMsg) """),format.raw/*58.46*/("""{"""),format.raw/*58.47*/("""
                    """),format.raw/*59.21*/("""alert('Unable to update toggle: ' + errMsg);
                    location.href=""""),_display_(/*60.37*/{routes.ToggleAdmin.togglesList}),format.raw/*60.69*/("""";
                """),format.raw/*61.17*/("""}"""),format.raw/*61.18*/("""
                """),format.raw/*62.17*/("""$.ajax("""),format.raw/*62.24*/("""{"""),format.raw/*62.25*/("""
                    """),format.raw/*63.21*/("""type: "POST",
                    url: """"),_display_(/*64.28*/{routes.ToggleController.update(queryParamSafeName)}),format.raw/*64.80*/("""",
                    data: JSON.stringify(item),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function(data) """),format.raw/*68.45*/("""{"""),format.raw/*68.46*/("""
                      """),format.raw/*69.23*/("""alert("Successfully updated toggle");
                      location.href=""""),_display_(/*70.39*/{routes.ToggleAdmin.togglesList}),format.raw/*70.71*/("""";
                    """),format.raw/*71.21*/("""}"""),format.raw/*71.22*/(""",
                    error: function(jqXHR, textStatus, errThrown) """),format.raw/*72.67*/("""{"""),format.raw/*72.68*/("""
                        """),format.raw/*73.25*/("""var err = jqXHR.responseJSON.errors[0]; //.data; // error
                        var data = err.data || "";
                        var error = err.error || "";
                        var errMsg = "Failed to update toggle. " + data + ", " + error;
                        handleError(errMsg);
                    """),format.raw/*78.21*/("""}"""),format.raw/*78.22*/("""
                """),format.raw/*79.17*/("""}"""),format.raw/*79.18*/(""");
            """),format.raw/*80.13*/("""}"""),format.raw/*80.14*/("""
        """),format.raw/*81.9*/("""</script>

        <meta name="viewport" content="width= device-width, initial-scale=1.0, maximum-scale=1.0, user-scale=0">
        <style>
                html,body """),format.raw/*85.27*/("""{"""),format.raw/*85.28*/("""
                    """),format.raw/*86.21*/("""border: 0 none;
                    font-family: 'Questrial',Verdana,arial,helvetica;
                    margin: 0 auto;
                    padding 0;
                """),format.raw/*90.17*/("""}"""),format.raw/*90.18*/("""
                """),format.raw/*91.17*/("""table """),format.raw/*91.23*/("""{"""),format.raw/*91.24*/("""
                    """),format.raw/*92.21*/("""margin: 0 auto;
                    max-width: 320px;
                """),format.raw/*94.17*/("""}"""),format.raw/*94.18*/("""
                """),format.raw/*95.17*/("""tr, td """),format.raw/*95.24*/("""{"""),format.raw/*95.25*/("""
                    """),format.raw/*96.21*/("""border-bottom: 0px
                """),format.raw/*97.17*/("""}"""),format.raw/*97.18*/("""
                """),format.raw/*98.17*/("""textarea """),format.raw/*98.26*/("""{"""),format.raw/*98.27*/("""
                    """),format.raw/*99.21*/("""resize: none;
                """),format.raw/*100.17*/("""}"""),format.raw/*100.18*/("""
        """),format.raw/*101.9*/("""</style>
    </head>
    <body>
        """),_display_(/*104.10*/header("Edit Toggle")),format.raw/*104.31*/("""
        """),format.raw/*105.9*/("""<br>
            <table id="editTable" style="display:none">
                <tr>
                    <td>Feature Name:</td>
                    <td><span id="feature_name"></span></td>
                </tr>
                <tr>
                    <td>Feature Type:</td>
                    <td>
                        <select id="type" name="FEATURE_TYPE">
                            <option selected value="Permanent">Permanent</option>
                            <option value="Project">Project</option>
                            <option value="Temporary">Temporary</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Feature Description:</td>
                    <td><textarea id="desc" name="FEATURE_DESCRIPTION" rows="5"></textarea></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="button" value="Save" onclick="saveToggle()"></td>
                </tr>
            </table>
    </body>
</html>
"""))}
  }

  def render(hbcUser:HbcUser,queryParamSafeName:String): play.twirl.api.HtmlFormat.Appendable = apply(hbcUser,queryParamSafeName)

  def f:((HbcUser,String) => play.twirl.api.HtmlFormat.Appendable) = (hbcUser,queryParamSafeName) => apply(hbcUser,queryParamSafeName)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Thu May 25 14:43:55 EDT 2017
                  SOURCE: /Users/461967/dev/toggle-service/app/views/add_edit_toggle.scala.html
                  HASH: ab84a945bc641ea7f4c0159c5ea6e5a6b81f77f2
                  MATRIX: 523->1|657->47|684->48|892->230|906->236|956->266|1048->332|1062->338|1119->375|1202->432|1216->438|1268->470|1516->691|1555->709|1611->737|1640->738|1686->756|1721->763|1750->764|1799->785|1866->825|1942->880|2111->1021|2140->1022|2191->1045|2581->1407|2610->1408|2706->1476|2735->1477|2788->1502|3113->1799|3142->1800|3187->1817|3216->1818|3259->1833|3288->1834|3372->1890|3401->1891|3446->1908|3610->2044|3639->2045|3688->2066|3735->2086|3783->2113|3830->2132|3859->2133|3900->2146|3929->2147|3995->2185|4024->2186|4069->2203|4108->2214|4137->2215|4173->2223|4202->2224|4263->2257|4752->2718|4781->2719|4826->2736|4855->2737|4930->2784|4959->2785|5008->2806|5116->2887|5169->2919|5216->2938|5245->2939|5290->2956|5325->2963|5354->2964|5403->2985|5471->3026|5544->3078|5773->3279|5802->3280|5853->3303|5956->3379|6009->3411|6060->3434|6089->3435|6185->3503|6214->3504|6267->3529|6610->3844|6639->3845|6684->3862|6713->3863|6756->3878|6785->3879|6821->3888|7015->4054|7044->4055|7093->4076|7290->4245|7319->4246|7364->4263|7398->4269|7427->4270|7476->4291|7574->4361|7603->4362|7648->4379|7683->4386|7712->4387|7761->4408|7824->4443|7853->4444|7898->4461|7935->4470|7964->4471|8013->4492|8072->4522|8102->4523|8139->4532|8208->4573|8251->4594|8288->4603
                  LINES: 19->1|22->1|23->2|28->7|28->7|28->7|29->8|29->8|29->8|30->9|30->9|30->9|35->14|35->14|37->16|37->16|39->18|39->18|39->18|40->19|41->20|41->20|43->22|43->22|44->23|49->28|49->28|50->29|50->29|51->30|56->35|56->35|57->36|57->36|58->37|58->37|60->39|60->39|61->40|63->42|63->42|64->43|64->43|64->43|65->44|65->44|66->45|66->45|68->47|68->47|69->48|69->48|69->48|69->48|69->48|70->49|77->56|77->56|78->57|78->57|79->58|79->58|80->59|81->60|81->60|82->61|82->61|83->62|83->62|83->62|84->63|85->64|85->64|89->68|89->68|90->69|91->70|91->70|92->71|92->71|93->72|93->72|94->73|99->78|99->78|100->79|100->79|101->80|101->80|102->81|106->85|106->85|107->86|111->90|111->90|112->91|112->91|112->91|113->92|115->94|115->94|116->95|116->95|116->95|117->96|118->97|118->97|119->98|119->98|119->98|120->99|121->100|121->100|122->101|125->104|125->104|126->105
                  -- GENERATED --
              */
          