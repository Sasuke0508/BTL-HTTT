//Clone the hidden element and shows it

$(".add-one").click(function () {
    console.log("Call function");
    $(".dynamic-stuff").append(`<tr style="vertical-align: middle;">
    <td>
        <div class="form-control">
            <input type="text" placeholder="Name"
                name="attribute" />
            <i class="fas fa-check-circle"></i>
            <i class="fas fa-exclamation-circle"></i>
            <small>Error message</small>
        </div>
    </td>
    <td>
        <div class="form-control">
            <input type="text" placeholder="Value" name="value" />
            <i class="fas fa-check-circle"></i>
            <i class="fas fa-exclamation-circle"></i>
            <small>Error message</small>
        </div>
    </td>
    <td>
        <div class="ms-2 mt-0">
            <a onclick="deleteAttribute(this)" class="btn btn-danger action delete"><i class="fa fa-times"></i></a>
        </div>
    </td>
</tr>`);
});

//Attach functionality to delete buttons


function deleteAttribute(btn) {
    var row = btn.parentNode.parentNode.parentNode;
    row.parentNode.removeChild(row);
}
