$(document).ready(function () {

    var AMOUNT_OF_DISHES_FOR_DAY = 3;
    var WEEK_DAYS = {
        MONDAY: 'monday',
        TUESDAY: 'tuesday',
        WEDNESDAY: 'wednesday',
        THURSDAY: 'thursday',
        FRIDAY: 'friday'
    };
    var amountOfDishesForWeek = {monday: 0, tuesday: 0, wednesday: 0, thursday: 0, friday: 0};
    var wrapper;

    var dishesListForSave = {monday: [], tuesday: [], wednesday: [], thursday: [], friday: []};

    $('.menu-item-addButton').click(addDish);

    //Ф-я додає страву до замовлення
    function addDish() {
        //successReporter.text("");
        var id = $(this).attr('id');
        var pushedAddButton = $('#' + id);

        /*Клонируем подпункт меню*/
        var clonedMenuItem = pushedAddButton.parent().clone();
        /*Убираем с него класс "добаления блюда в заказ", остальные классы сохраняем*/
        var classes = clonedMenuItem.find('div.btn').remove().removeClass('.menu-item-addButton').attr('class');
        var dishCategory = getDishCategory(pushedAddButton);
        var dayOfWeek = getDayOfWeek(pushedAddButton);

        /*Добавляем в список недели, в массив одного дня выбранные блюда по их id (id кнопки "+")*/
        dishesListForSave[dayOfWeek].push(id);

        /*Делаем прозрачной и недоступной категорию меню, которую выбираем*/
        $('.' + dayOfWeek + '.enabled-menu-items .' + dishCategory).addClass('disabled').css('opacity', '0.4').find('.btn').addClass('disabled').unbind('click', addDish);

        /*Создаем кнопку "удаления блюда из заказа", добавляем ей классы, ставим обработчик click()*/
        $('<div>-</div>').addClass(classes).attr("id", id + "addedDishesId").appendTo(clonedMenuItem).click(removeDish);
        /*if (!wrapper.children().is('div')) {
         wrapper.text('');
         }*/

        /*Делаем контейнер для добавленных элементов меню*/
        wrapper = $('.added-menu-items.' + dayOfWeek);
        /*Добавляем клонированные элементы в контейнер*/
        clonedMenuItem.appendTo(wrapper);
        /*Считаем общую стоемость за день*/
        amountOfDishesForWeek[dayOfWeek] = sum(dayOfWeek);
        console.log("amountOfDishesForWeek[" + dayOfWeek + "] = " + amountOfDishesForWeek[dayOfWeek]);
        /*console.log('WEEK_DAYS = ' + WEEK_DAYS[1]);*/
    }

    //Ф-я видаляє страву із замовлення
    function removeDish() {
        var pushedRemoveButton = $(this);

        /*Удаляем из массива удаленные блюда по их id (id кнопки "-")*/
        var removeButtonId = parseInt(pushedRemoveButton.attr('id'));

        var dishCategory = getDishCategory(pushedRemoveButton);
        var dayOfWeek = getDayOfWeek(pushedRemoveButton);

        /*Удаляем из списка недели, из массива одного дня удаленные блюда по их id (id кнопки "-")*/
        var index = dishesListForSave[dayOfWeek].indexOf('' + removeButtonId);
        dishesListForSave[dayOfWeek].splice(index, 1);

        pushedRemoveButton.parent().remove();
        /*Считаем общую стоемость за день*/
        sum(dayOfWeek);
        amountOfDishesForWeek[dayOfWeek] = sum(dayOfWeek);
        console.log("amountOfDishesForWeek[" + dayOfWeek + "] = " + amountOfDishesForWeek[dayOfWeek]);
        /*Делаем видимой категорию меню (в списке меню), которую удаляем из заказа*/
        $('.' + dayOfWeek + '.enabled-menu-items .' + dishCategory).removeClass('disabled').css('opacity', '1.0').find('.btn').removeClass('disabled').bind('click', addDish);
    }

    //Ф-я повертає категорію страви
    function getDishCategory(button) {
        var category;
        if (button.parent().is('.first-dish')) {
            category = "first-dish";
        } else if (button.parent().is('.second-dish')) {
            category = "second-dish";
        } else {
            category = "salad-dish";
        }
        return category;
    }

    //Ф-я повертає день замовлення
    function getDayOfWeek(button) {
        var day;
        if (button.parent().parent().is('.monday') || button.is('.monday')) {
            day = WEEK_DAYS.MONDAY;
        } else if (button.parent().parent().is('.tuesday') || button.is('.tuesday')) {
            day = WEEK_DAYS.TUESDAY;
        } else if (button.parent().parent().is('.wednesday') || button.is('.wednesday')) {
            day = WEEK_DAYS.WEDNESDAY;
        } else if (button.parent().parent().is('.thursday') || button.is('.thursday')) {
            day = WEEK_DAYS.THURSDAY;
        } else if (button.parent().parent().is('.friday') | button.is('.friday')) {
            day = WEEK_DAYS.FRIDAY;
        }
        return day;
    }

    $('#mondaySave').click(saveDishesForDay);
    $('#tuesdaySave').click(saveDishesForDay);
    $('#wednesdaySave').click(saveDishesForDay);
    $('#thursdaySave').click(saveDishesForDay);
    $('#fridaySave').click(saveDishesForDay);
    $('#weekSave').click(saveDishesForWeek);

    //Ф-я зберігає замовлення на один вибраний день
    function saveDishesForDay() {
        console.log(this);
        var dayForSave;
        dayForSave = getDayOfWeek($(this));
        console.log("save сработал, dayOfWeek = " + dayForSave);

        var dishesArray = dishesListForSave[dayForSave];

        if (amountOfDishesForWeek[dayForSave] == AMOUNT_OF_DISHES_FOR_DAY) {
            saveDishes(dishesArray);
        }
        if (amountOfDishesForWeek[dayForSave] < AMOUNT_OF_DISHES_FOR_DAY) {
            //Ховаємо помилку замовлення на весь тиждень і підтвердження успішного збереження
            $('#weekError').attr('class', 'hidden');
            $('#success').attr('class', 'hidden');
            //Показуємо помилку замовлення на один день
            $('#dayError').attr('class', 'visible');
            $('#modalForOrder').modal();
        }
    }

    //Ф-я зберігання їжі, яка використовується в ф-ях зберігання на день і на тиждень
    function saveDishes(arr) {
        $.each(dishesListForSave, function (index, value) {
            console.log('На вході dishesListForSave = ' + value);
        });
        $.ajax({
            url: "../user/saveOrder",
            type: "POST",
            data: {array: arr.join(',')},
            success: onAjaxSuccess
        });
    }

    function onAjaxSuccess(data) {
        console.log("data.toString() = " + data.toString());
        var itemsForRestore = data.split(",");
        var buttonAdd;
        var dishCategory;
        var dayOfWeek;
        $.each(itemsForRestore, function (index, value) {
            console.log("itemsForRestore:value = " + value);
            buttonAdd = $('#' + value);
            dishCategory = getDishCategory(buttonAdd);
            dayOfWeek = getDayOfWeek(buttonAdd);
            $('.' + dayOfWeek + '.enabled-menu-items .' + dishCategory).removeClass('disabled').css('opacity', '1.0').find('.btn').removeClass('disabled').bind('click', addDish);
            var id = dishesListForSave[dayOfWeek].indexOf(value);
            dishesListForSave[dayOfWeek].splice(id, 1);
            $('#' + value + 'addedDishesId').parent().removeClass().unbind('click', removeDish).remove();
            amountOfDishesForWeek[dayOfWeek] = 0;
            $('#' + dayOfWeek + 'Amount').text('0');
        });

        //Обнуляємо список страв
        $.each(dishesListForSave, function (i, val) {
            console.log('На виході dishesListForSave = ' + val);
        });

        //Ховаємо помилки
        $('#weekError').attr('class', 'hidden');
        $('#dayError').attr('class', 'hidden');
        //Показуємо підтвердження успішного збереження
        $('#success').attr('class', 'visible');
        $('#modalForOrder').modal();
    }

    //Ф-я зберігає замовлення на весь тиждень
    function saveDishesForWeek() {
        var skippingDays = [];
        $.each(amountOfDishesForWeek, function (index, value) {
            console.log("index = " + index + "; value = " + value);
            if (value != AMOUNT_OF_DISHES_FOR_DAY) {
                skippingDays.push(index);
            }
        });
        console.log("skippingDays = " + skippingDays);
        console.log("skippingDays.length = " + skippingDays.length);
        var dishesArray = [];
        if (skippingDays.length == 0) {
            $.each(dishesListForSave, function (index, value) {
                console.log('index!!!!! = ' + index + '; value!!!!! = ' + value);
                dishesArray = dishesArray.concat(value);
                console.log('dishesArray = ' + dishesArray);
            });
            saveDishes(dishesArray);
        } else {
            //Робимо невидимими всі дні у впливаючому вікні
            $('#weekError li').attr('class', 'hidden');
            //Ховаємо помилку замовлення на один день і підтвердження успішного збереження
            $('#dayError').attr('class', 'hidden');
            $('#success').attr('class', 'hidden');
            //Показуємо помилку замовлення на весь тиждень
            $('#weekError').attr('class', 'visible');
            //Робимо видимими всі незамовлені дні у впливаючому вікні
            $.each(skippingDays, function (index, value) {
                $('#' + value + 'Modal').attr('class', 'visible');
            });
            $('#modalForOrder').modal();
        }
    }

    /*Ф-я виводить вартість замовлених страв і повертає кількість замовлених страв за день*/
    function sum(dayOfWeek) {
        /*Знаходимо список елементів, де вказана вартість страв*/
        var costArray = $('.' + dayOfWeek + '.added-menu-items').find('.menu-item-cost');
        /*Просумовуємо вартість замовлення за день*/
        var amount = 0;
        costArray.each(function () {
            amount += parseInt($(this).text());
        });
        /*Виводимо вартість замовлення*/
        $('#' + dayOfWeek + 'Amount').text(amount);
        /*Повертаємо кількість вибраних страв за день*/
        return costArray.size();
    }

});