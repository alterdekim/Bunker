function str_toHex(s) {
    // utf8 to latin1
    var s = unescape(encodeURIComponent(s));
    var h = '';
    for (var i = 0; i < s.length; i++) {
        h += s.charCodeAt(i).toString(16);
    }
    return h;
}

function grab_form() {
    let arr = [];
    $("form#entryForm :input").each(function() {
         var input = $(this);
         let query = "";
         if(input.attr("type") == "checkbox") {
            query = input.attr('id') + "=" + input.is(':checked');
         } else if(input.attr("type") == "text") {
            var vv = str_toHex(input.val());
            query = input.attr('id') + "=" + vv;
         } else {
            query = input.attr('id') + "=" + input.val();
         }
         arr.push(query);
    });
    arr.push("section=" + new URL(window.location.href).searchParams.get("section"));
    return arr.join("&");
}

function add_entry_modal() {
    $("#modal_submit_edit").css("display", "none");
    $("#modal_submit_add").css("display", "");
}

function form_agge(jobj) {
    get_text_api(jobj.genderTextId, function(t) {
        $("#gender_text").val(t);
    });
    $("#ismale").prop('checked', jobj.isMale);
    $("#isfemale").prop('checked', jobj.isFemale);
    $("#childFree").prop('checked', jobj.isChildfree);
    $("#canDie").prop('checked', jobj.canDie);
}

function form_hobb(jobj) {
    get_text_api(jobj.textDescId, function(t) {
        $("#hobby_text").val(t);
    });
    $("#violenceRange").val(jobj.violence);
    $("#powerRange").val(jobj.power);
    $("#healRange").val(jobj.heal);
    $("#foodRange").val(jobj.food);
}

function form_lugg(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#luggage_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#luggage_desc_text").val(t);
    });
    $("#violenceRange").val(jobj.violence);
    $("#powerRange").val(jobj.power);
    $("#healRange").val(jobj.heal);
    $("#foodRange").val(jobj.food);
    $("#isgarbage").prop('checked', jobj.garbage);
}

function form_prof(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#work_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#work_desc_text").val(t);
    });
    $("#violenceRange").val(jobj.violence);
    $("#powerRange").val(jobj.power);
    $("#healRange").val(jobj.heal);
    $("#foodRange").val(jobj.food);
}

function form_heal(jobj) {
    get_text_api(jobj.textNameId, function(t) {
        $("#heal_name_text").val(t);
    });
    get_text_api(jobj.textDescId, function(t) {
        $("#heal_desc_text").val(t);
    });
    $("#health_index").val(jobj.health_index);
}

function form_disaster(jobj) {
    get_text_api(jobj.nameTextId, function(t) {
        $("#diss_name_text").val(t);
    });
    get_text_api(jobj.descTextId, function(t) {
        $("#diss_desc_text").val(t);
    });
}

function show_modal_edit(jobj, oid) {
    var section = new URL(window.location.href).searchParams.get("section");

    switch(section) {
        case "agge":
            form_agge(jobj);
            break;
        case "hobb":
            form_hobb(jobj);
            break;
        case "lugg":
            form_lugg(jobj);
            break;
        case "heal":
            form_heal(jobj);
            break;
        case "prof":
            form_prof(jobj);
            break;
        case "diss":
            form_disaster(jobj);
            break;
        default:
            form_disaster(jobj);
            break;
    }

    $("#modal_submit_edit").css("display", "");
    $("#modal_submit_add").css("display", "none");
    $("#modal_submit_edit").attr("data-entry-id", oid);
}

function edit_submit_entry(obj) {
    $.post("/api/remove_entry", "section="+new URL(window.location.href).searchParams.get("section")+"&entry_id="+($(obj).attr("data-entry-id")), function(data, status) {
        $.post("/api/add_entry", grab_form(), function(data, status) {
            window.location.reload();
        });
    });
}

function edit_entry(obj) {
    $.post("/api/edit_entry", "section="+new URL(window.location.href).searchParams.get("section")+"&entry_id="+($(obj).attr("data-id")), function(data, status) {
        var jobj = JSON.parse(data);
        show_modal_edit(jobj, $(obj).attr("data-id"));
    });
}

function get_text_api(tid, cb) {
    $.post("/api/getTextById", "entry_id="+tid, function(data, status) {
        cb(data);
    });
}

function remove_entry(obj) {
    $.post("/api/remove_entry", "section="+new URL(window.location.href).searchParams.get("section")+"&entry_id="+($(obj).attr("data-id")), function(data, status) {
        window.location.reload();
    });
}

function add_entry() {
   $.post("/api/add_entry", grab_form(), function(data, status) {
        window.location.reload();
   });
}