//Инициализация Yandex.Map
var map;
ymaps.ready(
        function () { //обработчик успеха
          map = new ymaps.Map("map", {
            center: [55.76, 37.64],
            zoom: 7,
            controls: ["smallMapDefaultSet"]
          });

          findAll();
        },
        function (err) { //обработчик ошибки
          console.log(err);
        }
);

//Установить обработчик для кнопки "Добавить"
$('#btnAdd').on('click', function () {
  geocode($('#pointAddress').val());
});

//Вычитать сохраненные точки
function findAll() {
  $.ajax({
    type: 'GET',
    url: 'rest/points',
    dataType: "json",
    success: drawPoints
  });
}

// Сохранить новую точку
function savePoint(point) {
  $.ajax({
    type: 'POST',
    contentType: 'application/json',
    url: 'rest/points',
    dataType: "json",
    data: JSON.stringify(point),
    success: function (data, textStatus, jqXHR) {
      drawPoint(point);
      $('#pointName').val('');
      $('#pointAddress').val('');
    },
    error: function (jqXHR, textStatus, errorThrown) {
      alert(jqXHR.responseText);
    }
  });
}

//Отобразить точки на странице
function drawPoints(data) {
  var points = data === null ? [] : (data instanceof Array ? data : [data]);
  $('#points').empty();
  $.each(points, function (index, point) {
    drawPoint(point);
  });
}

//Отобразить точку на странице (в списке и на карте)
function drawPoint(point) {
  var btn = $('<a href="rest/points/' + encodeURIComponent(point.name) + '" class="btn btn-link">' + point.name + '</a>');
  $('#points').append(btn);

  map.geoObjects.add(
          new ymaps.Placemark(
                  [point.latitude, point.longitude],
                  {iconContent: point.name},
          {preset: 'islands#blueStretchyIcon'}
          )
          );
}

//Выполнить прямое геокодирование
function geocode(address){
  $.ajax({
    type: 'GET',
    url: 'rest/geocode?address=' + encodeURIComponent(address),
    success: function (data, textStatus, jqXHR) {
      var points = data === null ? [] : (data instanceof Array ? data : [data]);
      
      if (points.length === 1) {
        //добавляем имя точки
        points[0].name = $('#pointName').val();
        //сохраняем точку
        savePoint(points[0]);
      } else {
        //требуется уточнение адреса
        completeAddress(points);
      }
    },
    error: function (jqXHR, textStatus, errorThrown) {
      alert(jqXHR.responseText);
    }
  });
}

//Отобразить диалог уточнения адреса
function completeAddress(points) {
  $('#listAddress').empty();
  $.each(points, function (index, point) {
    addAddressLine(point);
  });
  $('#addressModal').modal('show');
}

//Добавить в диалог уточнения адреса строку с адресом 
function addAddressLine(point) {
  var line = $('<li class="list-group-item">' + point.address + '</li>');
  line.on('click', function () {
    $('#addressModal').modal('hide');
    //добавляем имя точки
    point.name = $('#pointName').val();
    //сохраняем точку
    savePoint(point);
  });
  $('#listAddress').append(line);
}