var map;

ymaps.ready(
        function () { //обработчик успеха
          map = new ymaps.Map("map", {
            center: [55.76, 37.64],
            zoom: 7
          });

          findAll();
        },
        function (err) { //обработчик ошибки
          console.log(err);
        }
);

$('#btnAdd').on('click', function () {
  createPoint($('#pointName').val(), $('#pointAddress').val());
  return false;
});

function findAll() {
  $.ajax({
    type: 'GET',
    url: 'rest/points',
    dataType: "json",
    success: renderList
  });
}


function createPoint(name, address) {
  $.ajax({
    type: 'POST',
    contentType: 'application/json',
    url: 'rest/points',
    dataType: "json",
    data: JSON.stringify({'name': name, 'address': address}),
    success: function (data, textStatus, jqXHR) {
      var points = data == null ? [] : (data instanceof Array ? data : [data]);
      if (points.length == 1) {
        addPoint(points[0]);
      } else {
        $('#listAddress').empty();
        $.each(points, function (index, point) {
          addAddress(point);
        });
        $('#addressModal').modal('show')
      }
    },
    error: function (jqXHR, textStatus, errorThrown) {
      alert(jqXHR.responseText);
    }
  });
}

function addPoint(point) {
  var btn = $('<button type="button" class="btn btn-link">' + point.name + '</button>');
  btn.bind('click', function () {
    console.log(point.name);
  });
  $('#points').append(btn);

  map.geoObjects.add(
          new ymaps.Placemark(
                  [point.latitude, point.longitude],
                  {iconContent: point.name},
          {preset: 'islands#blueStretchyIcon'}
          )
          );
}

function addAddress(point){
  var line = $('<li class="list-group-item">' + point.address + '</li>');
  line.on('click', function(){
    $('#addressModal').modal('hide')
    $('#pointAddress').val(point.address);
  });
  $('#listAddress').append(line);
}

function renderList(data) {
  var points = data == null ? [] : (data instanceof Array ? data : [data]);

  $('#points').empty();

  $.each(points, function (index, point) {
    addPoint(point);
  });
}
