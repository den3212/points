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

function findAll() {
    console.log('findAll');
    $.ajax({
        type: 'GET',
        url: 'rest/points',
        dataType: "json",
        success: renderList
    });
}

function renderList(data) {
    var list = data == null ? [] : (data instanceof Array ? data : [data]);

    $('#points').empty();
    $.each(list, function (index, point) {
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
    });
}