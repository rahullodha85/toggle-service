
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
object toggles extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template1[HbcUser,play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply/*1.2*/(hbcUser: HbcUser):play.twirl.api.HtmlFormat.Appendable = {
      _display_ {

Seq[Any](format.raw/*1.20*/("""

"""),format.raw/*3.1*/("""<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <title>Toggles</title>
    <link rel="stylesheet" type="text/css" href=""""),_display_(/*9.51*/routes/*9.57*/.Assets.at("css/feature_toggles.css")),format.raw/*9.94*/("""">
    <link rel="stylesheet" type="text/css" href=""""),_display_(/*10.51*/routes/*10.57*/.Assets.at("css/jquery-ui.min.css")),format.raw/*10.92*/("""">
    <script type="text/javascript" src=""""),_display_(/*11.42*/routes/*11.48*/.Assets.at("js/underscore-min.js")),format.raw/*11.82*/(""""></script>
    <script type="text/javascript" src=""""),_display_(/*12.42*/routes/*12.48*/.Assets.at("js/jquery.min.js")),format.raw/*12.78*/(""""></script>
    <script type="text/javascript" src=""""),_display_(/*13.42*/routes/*13.48*/.Assets.at("js/jquery-ui.min.js")),format.raw/*13.81*/(""""></script>
    <script type="text/javascript">
        var userName = """"),_display_(/*15.26*/{hbcUser.username}),format.raw/*15.44*/("""";
        window.partial = function(which, data) """),format.raw/*16.48*/("""{"""),format.raw/*16.49*/("""
            """),format.raw/*17.13*/("""var templateString = $('#' + which + '-partial').html(),
                compiled = _.template(templateString);
            return compiled(data);
        """),format.raw/*20.9*/("""}"""),format.raw/*20.10*/(""";
        $(function()"""),format.raw/*21.21*/("""{"""),format.raw/*21.22*/("""
            """),format.raw/*22.13*/("""$('#create-toggle').dialog("""),format.raw/*22.40*/("""{"""),format.raw/*22.41*/("""
                """),format.raw/*23.17*/("""autoOpen: false,
                height: 600,
                width: 450,
                modal: true,
                resizable: false,
                buttons: """),format.raw/*28.26*/("""{"""),format.raw/*28.27*/("""
                    """),format.raw/*29.21*/("""Create: function() """),format.raw/*29.40*/("""{"""),format.raw/*29.41*/("""
                        """),format.raw/*30.25*/("""createToggle();
                    """),format.raw/*31.21*/("""}"""),format.raw/*31.22*/(""",
                    Cancel: function() """),format.raw/*32.40*/("""{"""),format.raw/*32.41*/("""
                        """),format.raw/*33.25*/("""$(this).dialog('close');
                    """),format.raw/*34.21*/("""}"""),format.raw/*34.22*/("""
                """),format.raw/*35.17*/("""}"""),format.raw/*35.18*/(""",
                close: function() """),format.raw/*36.35*/("""{"""),format.raw/*36.36*/("""
                    """),format.raw/*37.21*/("""$('#createForm')[0].reset();
                """),format.raw/*38.17*/("""}"""),format.raw/*38.18*/("""
            """),format.raw/*39.13*/("""}"""),format.raw/*39.14*/(""");

            $.ajax("""),format.raw/*41.20*/("""{"""),format.raw/*41.21*/("""
                """),format.raw/*42.17*/("""type: 'GET',
                url: '"""),_display_(/*43.24*/{routes.ToggleController.getToggles("")}),format.raw/*43.64*/("""',
                success: function(data) """),format.raw/*44.41*/("""{"""),format.raw/*44.42*/("""
                    """),format.raw/*45.21*/("""var toggleGroups = """),format.raw/*45.40*/("""{"""),format.raw/*45.41*/("""
                        """),format.raw/*46.25*/("""'toggleGroups':
                            _.groupBy(
                                _.map(
                                    data.response.results,
                                    function (toggle) """),format.raw/*50.55*/("""{"""),format.raw/*50.56*/("""
                                        """),format.raw/*51.41*/("""toggle.description = toggle.description ? toggle.description : "";
                                        return toggle;
                                    """),format.raw/*53.37*/("""}"""),format.raw/*53.38*/("""
                                """),format.raw/*54.33*/("""),
                                function (toggle) """),format.raw/*55.51*/("""{"""),format.raw/*55.52*/("""
                                    """),format.raw/*56.37*/("""return toggle.toggle_type;
                                """),format.raw/*57.33*/("""}"""),format.raw/*57.34*/("""
                            """),format.raw/*58.29*/(""")

                    """),format.raw/*60.21*/("""}"""),format.raw/*60.22*/(""",
                    templateString = $('#toggle-groups-template').html(),
                    compiled = _.template(templateString);
                    $('#header').after(
                        compiled(toggleGroups)
                    );
                    $('.content').show();
                """),format.raw/*67.17*/("""}"""),format.raw/*67.18*/(""",
                failure: function(errMsg) """),format.raw/*68.43*/("""{"""),format.raw/*68.44*/("""
                    """),format.raw/*69.21*/("""alert('Unable to retrieve toggles: ' + errMsg);
                """),format.raw/*70.17*/("""}"""),format.raw/*70.18*/(""",
                complete: function (request, status) """),format.raw/*71.54*/("""{"""),format.raw/*71.55*/("""
                    """),format.raw/*72.21*/("""if (request.responseJSON.response.status === 'error') """),format.raw/*72.75*/("""{"""),format.raw/*72.76*/("""
                        """),format.raw/*73.25*/("""alert('Unable to retrieve toggles: ' + request.responseJSON.errors[0].data + ' (' + request.responseJSON.errors[0].error + ')');
                    """),format.raw/*74.21*/("""}"""),format.raw/*74.22*/("""
                """),format.raw/*75.17*/("""}"""),format.raw/*75.18*/("""
            """),format.raw/*76.13*/("""}"""),format.raw/*76.14*/(""");

            $(document).on('click','.feature-row',function()"""),format.raw/*78.61*/("""{"""),format.raw/*78.62*/("""
                """),format.raw/*79.17*/("""var $sibling = $(this).next('.feature-details-row');
                if ($sibling.is(':hidden')) """),format.raw/*80.45*/("""{"""),format.raw/*80.46*/("""
                    """),format.raw/*81.21*/("""$sibling.show(350);
                """),format.raw/*82.17*/("""}"""),format.raw/*82.18*/(""" """),format.raw/*82.19*/("""else """),format.raw/*82.24*/("""{"""),format.raw/*82.25*/("""
                    """),format.raw/*83.21*/("""$sibling.hide(350);
                """),format.raw/*84.17*/("""}"""),format.raw/*84.18*/("""
            """),format.raw/*85.13*/("""}"""),format.raw/*85.14*/(""");

            $('.feature-details-row').hide();

            $('.logoff-button').click(function(event)"""),format.raw/*89.54*/("""{"""),format.raw/*89.55*/("""
                """),format.raw/*90.17*/("""event.preventDefault();
                var confirmation = confirm('Are you sure you want log off?');
                if (confirmation) """),format.raw/*92.35*/("""{"""),format.raw/*92.36*/("""
                    """),format.raw/*93.21*/("""window.location = '"""),_display_(/*93.41*/{routes.ToggleAdmin.logout}),format.raw/*93.68*/("""';
                """),format.raw/*94.17*/("""}"""),format.raw/*94.18*/("""
            """),format.raw/*95.13*/("""}"""),format.raw/*95.14*/(""");

            $('.new-button').click(function(event) """),format.raw/*97.52*/("""{"""),format.raw/*97.53*/("""
                """),format.raw/*98.17*/("""$('#create-toggle').dialog('open');
                $(".ui-dialog-titlebar").hide();
            """),format.raw/*100.13*/("""}"""),format.raw/*100.14*/(""");

            $(document).on('click','.delete',function(event) """),format.raw/*102.62*/("""{"""),format.raw/*102.63*/("""
                """),format.raw/*103.17*/("""var featureName = $(this).data('feature'),
                    validate = false;
                event.stopPropagation();
                validate = confirm('Are you sure you want to delete the ' + featureName + ' toggle?');
                if (validate) """),format.raw/*107.31*/("""{"""),format.raw/*107.32*/("""
                    """),format.raw/*108.21*/("""$.ajax("""),format.raw/*108.28*/("""{"""),format.raw/*108.29*/("""
                        """),format.raw/*109.25*/("""type: 'DELETE',
                        url: '/v1/toggle-service/toggles/' + featureName,
                        success: function(data)"""),format.raw/*111.48*/("""{"""),format.raw/*111.49*/("""
                            """),format.raw/*112.29*/("""$('[id="' + featureName + '_row"]').hide(350);
                        """),format.raw/*113.25*/("""}"""),format.raw/*113.26*/(""",
                        error: function(jqXHR, textStatus, errThrown) """),format.raw/*114.71*/("""{"""),format.raw/*114.72*/("""
                            """),format.raw/*115.29*/("""var err = jqXHR.responseJSON.errors[0];
                            var data = err.data || "";
                            var error = err.error || "";
                            var errMsg = "Failed to delete toggle. " + data + ", " + error;
                            alert(errMsg);
                        """),format.raw/*120.25*/("""}"""),format.raw/*120.26*/("""
                    """),format.raw/*121.21*/("""}"""),format.raw/*121.22*/(""");
                """),format.raw/*122.17*/("""}"""),format.raw/*122.18*/("""
            """),format.raw/*123.13*/("""}"""),format.raw/*123.14*/(""");

			$(document).on('click','.update',function(event) """),format.raw/*125.53*/("""{"""),format.raw/*125.54*/("""
                """),format.raw/*126.17*/("""var featureName = $(this).data('feature');
			    event.stopPropagation();
                location.href='/v1/toggle-service/ui/editToggles?featureName='+featureName;
			"""),format.raw/*129.4*/("""}"""),format.raw/*129.5*/(""");

		    $(document).on('click','.info',function(event) """),format.raw/*131.54*/("""{"""),format.raw/*131.55*/("""
                """),format.raw/*132.17*/("""var $sibling = $(this).parent().parent().next('.feature-details-row');
                event.stopPropagation();
                if ($sibling.is(':hidden')) """),format.raw/*134.45*/("""{"""),format.raw/*134.46*/("""
                    """),format.raw/*135.21*/("""$sibling.show(350);
                """),format.raw/*136.17*/("""}"""),format.raw/*136.18*/(""" """),format.raw/*136.19*/("""else """),format.raw/*136.24*/("""{"""),format.raw/*136.25*/("""
                    """),format.raw/*137.21*/("""$sibling.hide(350);
                """),format.raw/*138.17*/("""}"""),format.raw/*138.18*/("""
	        """),format.raw/*139.10*/("""}"""),format.raw/*139.11*/(""");

	        $(document).on('click','.toggle_switch',function(event) """),format.raw/*141.66*/("""{"""),format.raw/*141.67*/("""
                """),format.raw/*142.17*/("""var toggleName = $(this).data('feature'),
                    state = $(this).data('state'),
                    item = """),format.raw/*144.28*/("""{"""),format.raw/*144.29*/("""'item': """),format.raw/*144.37*/("""{"""),format.raw/*144.38*/("""
                                """),format.raw/*145.33*/("""'toggle_name' : toggleName,
                                'toggle_state' : !state,
                                'created_timestamp' : '',
                                'modified_timestamp' : '',
                                'toggle_type' : '',
                                'modified_by' : userName
                              """),format.raw/*151.31*/("""}"""),format.raw/*151.32*/("""
                           """),format.raw/*152.28*/("""}"""),format.raw/*152.29*/(""",
                    validate = false;
                event.stopPropagation();
                validate = confirm('Are you sure you want to turn the ' + toggleName + ' toggle ' + (state ? "off" : "on") + '?');
                if (validate) """),format.raw/*156.31*/("""{"""),format.raw/*156.32*/("""
                    """),format.raw/*157.21*/("""$.ajax("""),format.raw/*157.28*/("""{"""),format.raw/*157.29*/("""
                        """),format.raw/*158.25*/("""type: 'POST',
                        url: '/v1/toggle-service/toggles/' + toggleName,
                        data: JSON.stringify(item),
                        contentType: 'application/json; charset=utf-8',
                        dataType: 'json',
                        success: function(data) """),format.raw/*163.49*/("""{"""),format.raw/*163.50*/("""
                            """),format.raw/*164.29*/("""$('[id="' + toggleName + '_img"]').prop('src','/v1/toggle-service/assets/images/toggle' + (state ? 'Off' : 'On') + '.gif').data('state',!state);
                        """),format.raw/*165.25*/("""}"""),format.raw/*165.26*/(""",
                        error: function(jqXHR, textStatus, errThrown) """),format.raw/*166.71*/("""{"""),format.raw/*166.72*/("""
                            """),format.raw/*167.29*/("""var err = jqXHR.responseJSON.errors[0];
                            var data = err.data || "";
                            var error = err.error || "";
                            var errMsg = "Failed to update toggle. " + data + ", " + error;
                            alert(errMsg);
                        """),format.raw/*172.25*/("""}"""),format.raw/*172.26*/("""
                    """),format.raw/*173.21*/("""}"""),format.raw/*173.22*/(""");
                """),format.raw/*174.17*/("""}"""),format.raw/*174.18*/("""
	        """),format.raw/*175.10*/("""}"""),format.raw/*175.11*/(""");
	    """),format.raw/*176.6*/("""}"""),format.raw/*176.7*/(""");
	    function createToggle() """),format.raw/*177.30*/("""{"""),format.raw/*177.31*/("""
            """),format.raw/*178.13*/("""var toggleName = $('#createForm #name').val(),
                state = $('#createForm #state').val() === "true",
                desc = $('#createForm #desc').val(),
                type = $('#createForm #type').val(),
                item = """),format.raw/*182.24*/("""{"""),format.raw/*182.25*/("""'item': """),format.raw/*182.33*/("""{"""),format.raw/*182.34*/("""
                            """),format.raw/*183.29*/("""'toggle_name' : toggleName,
                            'toggle_state' : state,
                            'created_timestamp' : '',
                            'modified_timestamp' : '',
                            "description" : desc,
                            'toggle_type' : type,
                            'modified_by' : userName
                          """),format.raw/*190.27*/("""}"""),format.raw/*190.28*/("""
                """),format.raw/*191.17*/("""}"""),format.raw/*191.18*/(""";
                
            $.ajax("""),format.raw/*193.20*/("""{"""),format.raw/*193.21*/("""
                """),format.raw/*194.17*/("""type: 'PUT',
                url: '/v1/toggle-service/toggles/' + toggleName,
                data: JSON.stringify(item),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function(data) """),format.raw/*199.41*/("""{"""),format.raw/*199.42*/("""
                    """),format.raw/*200.21*/("""alert('Successfully created toggle');
                    location.href='"""),_display_(/*201.37*/{routes.ToggleAdmin.togglesList}),format.raw/*201.69*/("""';
                """),format.raw/*202.17*/("""}"""),format.raw/*202.18*/(""",
                error: function(jqXHR, textStatus, errThrown) """),format.raw/*203.63*/("""{"""),format.raw/*203.64*/("""
                    """),format.raw/*204.21*/("""var err = jqXHR.responseJSON.errors[0];
                    var data = err.data || "";
                    var error = err.error || "";
                    var errMsg = "Failed to create toggle. " + data + ", " + error;
                    alert(errMsg);
                """),format.raw/*209.17*/("""}"""),format.raw/*209.18*/("""
            """),format.raw/*210.13*/("""}"""),format.raw/*210.14*/(""");
	    """),format.raw/*211.6*/("""}"""),format.raw/*211.7*/("""
    """),format.raw/*212.5*/("""</script>
    <script id="toggle-groups-template" type="text/x-handlebars-template">
        <% _(toggleGroups).each(function(toggles, groupName) """),format.raw/*214.62*/("""{"""),format.raw/*214.63*/(""" """),format.raw/*214.64*/("""%>
        <%= partial('group', """),format.raw/*215.30*/("""{"""),format.raw/*215.31*/("""'groupName': groupName"""),format.raw/*215.53*/("""}"""),format.raw/*215.54*/(""") %>
        <% _(toggles).each(function(toggle) """),format.raw/*216.45*/("""{"""),format.raw/*216.46*/(""" """),format.raw/*216.47*/("""%>
        <%= partial('toggle', toggle) %>
        <% """),format.raw/*218.12*/("""}"""),format.raw/*218.13*/(""")"""),format.raw/*218.14*/("""}"""),format.raw/*218.15*/(""") %>
    </script>
    <script id="group-partial" type="text/x-handlebars-template">
        <tr class="feature-type-row">
            <td colspan="5" bgcolor="#6D7B8D" style="color:#ffffff; font-size:10px;"><%= groupName %></td>
        </tr>
    </script>
    <script id="toggle-partial" type="text/x-handlebars-template">
        <tr id="<%= toggle_name %>_row"  bgcolor="#ffffff" style="font-size:10px;" class="feature-row">
            <td><%= toggle_name %></td>
            <td align="right">
                <img class="toggle_switch" data-feature="<%= toggle_name %>" data-state="<%= toggle_state %>" id="<%= toggle_name %>_img" img src="/v1/toggle-service/assets/images/toggle<%= toggle_state ? "On" : "Off"%>.gif" border="0"/>
            </td>
            <td><input type="button" data-feature="<%= toggle_name %>" class="info" value="Info"/></td>
            <td><input type="button" data-feature="<%= toggle_name %>" class="delete" value="Delete"/></td>
            <td><input type="button" data-feature="<%= toggle_name %>" class="update" value="Edit"/></td>
        </tr>
        <tr class="feature-details-row" style="display: none;">
            <td colspan="5" bgcolor="#ffffff" styles="font-size: 10px;">
              <p>Feature Description: <%= description %></p>
              <p>Feature Type: <%= toggle_type %></p>
              <p><a href="/v1/toggle-service/ui/toggles/<%= toggle_name %>/history">Complete History</a></p>
            </td>
        </tr>
    </script>
	</head>
	<body>
    """),_display_(/*245.6*/header("Toggles")),format.raw/*245.23*/("""

	"""),format.raw/*247.2*/("""<br>
		
	<div class="content" style="display:none">

	<table cellpadding="10px" cellspacing="0px" border="0" width="320px">
		<tbody>
			<tr>
				<td colspan="5" bgcolor="#000000" style="color:#ffffff; font-weight:bold;" align="left">Current Toggles</td>
            </tr>
            <tr id="header">
                <td bgcolor="#c1c1c1" style="font-weight:bold;">Feature Name</td>
				<td bgcolor="#c1c1c1" style="font-weight:bold;" align="center">Status</td>
                <td colspan="3" bgcolor="#c1c1c1" style="font-weight:bold;" align="center">Actions</td>
			</tr>
		</tbody>
	</table>
        <div id="create-toggle">
            <form id="createForm">
                <h2>Create Feature Toggle</h2>
                <p>Enter name and state for the new switch</p>
                <label for="name">Name</label>
                <input id="name" type="text" size="30" maxlength="50">
                <label for="state">State</label>
                <select id="state">
                    <option value="true">On</option>
                    <option value="false" selected>Off</option>
                </select>
                <label for="desc">Description</label>
                <textarea id="desc" rows="5"></textarea>
                <label for="type">Type</label>
                <select id="type">
                    <option selected="">Permanent</option>
                    <option>Project</option>
                    <option>Temporary</option>
                </select>
            </form>
        </div>
	</div>
</body>
</html>
"""))}
  }

  def render(hbcUser:HbcUser): play.twirl.api.HtmlFormat.Appendable = apply(hbcUser)

  def f:((HbcUser) => play.twirl.api.HtmlFormat.Appendable) = (hbcUser) => apply(hbcUser)

  def ref: this.type = this

}
              /*
                  -- GENERATED --
                  DATE: Thu May 25 14:43:56 EDT 2017
                  SOURCE: /Users/461967/dev/toggle-service/app/views/toggles.scala.html
                  HASH: 9573d59b747193977abcf82856fb15a15ec1b003
                  MATRIX: 508->1|614->19|642->21|965->318|979->324|1036->361|1116->414|1131->420|1187->455|1258->499|1273->505|1328->539|1408->592|1423->598|1474->628|1554->681|1569->687|1623->720|1723->793|1762->811|1840->861|1869->862|1910->875|2092->1030|2121->1031|2171->1053|2200->1054|2241->1067|2296->1094|2325->1095|2370->1112|2560->1274|2589->1275|2638->1296|2685->1315|2714->1316|2767->1341|2831->1377|2860->1378|2929->1419|2958->1420|3011->1445|3084->1490|3113->1491|3158->1508|3187->1509|3251->1545|3280->1546|3329->1567|3402->1612|3431->1613|3472->1626|3501->1627|3552->1650|3581->1651|3626->1668|3689->1704|3750->1744|3821->1787|3850->1788|3899->1809|3946->1828|3975->1829|4028->1854|4263->2061|4292->2062|4361->2103|4547->2261|4576->2262|4637->2295|4718->2348|4747->2349|4812->2386|4899->2445|4928->2446|4985->2475|5036->2498|5065->2499|5396->2802|5425->2803|5497->2847|5526->2848|5575->2869|5667->2933|5696->2934|5779->2989|5808->2990|5857->3011|5939->3065|5968->3066|6021->3091|6198->3240|6227->3241|6272->3258|6301->3259|6342->3272|6371->3273|6463->3337|6492->3338|6537->3355|6662->3452|6691->3453|6740->3474|6804->3510|6833->3511|6862->3512|6895->3517|6924->3518|6973->3539|7037->3575|7066->3576|7107->3589|7136->3590|7268->3694|7297->3695|7342->3712|7506->3848|7535->3849|7584->3870|7631->3890|7679->3917|7726->3936|7755->3937|7796->3950|7825->3951|7908->4006|7937->4007|7982->4024|8108->4121|8138->4122|8232->4187|8262->4188|8308->4205|8592->4460|8622->4461|8672->4482|8708->4489|8738->4490|8792->4515|8958->4652|8988->4653|9046->4682|9146->4753|9176->4754|9277->4826|9307->4827|9365->4856|9705->5167|9735->5168|9785->5189|9815->5190|9863->5209|9893->5210|9935->5223|9965->5224|10050->5280|10080->5281|10126->5298|10324->5468|10353->5469|10439->5526|10469->5527|10515->5544|10700->5700|10730->5701|10780->5722|10845->5758|10875->5759|10905->5760|10939->5765|10969->5766|11019->5787|11084->5823|11114->5824|11153->5834|11183->5835|11281->5904|11311->5905|11357->5922|11506->6042|11536->6043|11573->6051|11603->6052|11665->6085|12035->6426|12065->6427|12122->6455|12152->6456|12423->6698|12453->6699|12503->6720|12539->6727|12569->6728|12623->6753|12953->7054|12983->7055|13041->7084|13239->7253|13269->7254|13370->7326|13400->7327|13458->7356|13798->7667|13828->7668|13878->7689|13908->7690|13956->7709|13986->7710|14025->7720|14055->7721|14091->7729|14120->7730|14181->7762|14211->7763|14253->7776|14524->8018|14554->8019|14591->8027|14621->8028|14679->8057|15076->8425|15106->8426|15152->8443|15182->8444|15249->8482|15279->8483|15325->8500|15614->8760|15644->8761|15694->8782|15796->8856|15850->8888|15898->8907|15928->8908|16021->8972|16051->8973|16101->8994|16401->9265|16431->9266|16473->9279|16503->9280|16539->9288|16568->9289|16601->9294|16776->9440|16806->9441|16836->9442|16897->9474|16927->9475|16978->9497|17008->9498|17086->9547|17116->9548|17146->9549|17230->9604|17260->9605|17290->9606|17320->9607|18864->11124|18903->11141|18934->11144
                  LINES: 19->1|22->1|24->3|30->9|30->9|30->9|31->10|31->10|31->10|32->11|32->11|32->11|33->12|33->12|33->12|34->13|34->13|34->13|36->15|36->15|37->16|37->16|38->17|41->20|41->20|42->21|42->21|43->22|43->22|43->22|44->23|49->28|49->28|50->29|50->29|50->29|51->30|52->31|52->31|53->32|53->32|54->33|55->34|55->34|56->35|56->35|57->36|57->36|58->37|59->38|59->38|60->39|60->39|62->41|62->41|63->42|64->43|64->43|65->44|65->44|66->45|66->45|66->45|67->46|71->50|71->50|72->51|74->53|74->53|75->54|76->55|76->55|77->56|78->57|78->57|79->58|81->60|81->60|88->67|88->67|89->68|89->68|90->69|91->70|91->70|92->71|92->71|93->72|93->72|93->72|94->73|95->74|95->74|96->75|96->75|97->76|97->76|99->78|99->78|100->79|101->80|101->80|102->81|103->82|103->82|103->82|103->82|103->82|104->83|105->84|105->84|106->85|106->85|110->89|110->89|111->90|113->92|113->92|114->93|114->93|114->93|115->94|115->94|116->95|116->95|118->97|118->97|119->98|121->100|121->100|123->102|123->102|124->103|128->107|128->107|129->108|129->108|129->108|130->109|132->111|132->111|133->112|134->113|134->113|135->114|135->114|136->115|141->120|141->120|142->121|142->121|143->122|143->122|144->123|144->123|146->125|146->125|147->126|150->129|150->129|152->131|152->131|153->132|155->134|155->134|156->135|157->136|157->136|157->136|157->136|157->136|158->137|159->138|159->138|160->139|160->139|162->141|162->141|163->142|165->144|165->144|165->144|165->144|166->145|172->151|172->151|173->152|173->152|177->156|177->156|178->157|178->157|178->157|179->158|184->163|184->163|185->164|186->165|186->165|187->166|187->166|188->167|193->172|193->172|194->173|194->173|195->174|195->174|196->175|196->175|197->176|197->176|198->177|198->177|199->178|203->182|203->182|203->182|203->182|204->183|211->190|211->190|212->191|212->191|214->193|214->193|215->194|220->199|220->199|221->200|222->201|222->201|223->202|223->202|224->203|224->203|225->204|230->209|230->209|231->210|231->210|232->211|232->211|233->212|235->214|235->214|235->214|236->215|236->215|236->215|236->215|237->216|237->216|237->216|239->218|239->218|239->218|239->218|266->245|266->245|268->247
                  -- GENERATED --
              */
          