

function initContainer() {

    $imgs = $('.container .img-holder');
    l = $imgs.length;
    radius = 400;

    TweenMax.set($('.container'), {
        css: {
            transformStyle: 'preserve-3d',
            perspective: 800,
            perspectiveOrigin: '50% 50%'
        }
    });

    posArray = [];
    totalImgToView = 5;
    imgMinus = 0.6301;
    angle = 0;

    $imgs.each(function (i, item) {

        angle = i * 0.63;
        //console.log('angle ',angle);
        var zPos = -Math.abs(angle * (100));

        var xPos = Math.sin(angle) * radius;
        posArray.push({x: xPos, z: zPos, angle: angle});
        var imgAlpha = (Math.ceil(0.5 * totalImgToView) * imgMinus) * 100;
        //imgAlpha = Math.abs(zPos) < imgAlpha ? 1 : 0;
        TweenMax.to(item, 1, {x: xPos, z: zPos, ease: Expo.easeOut, autoAlpha: 0});
    });

    curImgViewIndex = 0;
    targetImgViewIndex = 0;
    curIntervalId = 0;
    scrollbarDragging = false;

}



function rotate() {
    let minusVal = targetImgViewIndex - curImgViewIndex > 0 ? -0.6301 : 0.6301;

    let easeObj;
    let tweenTime;
    if (Math.abs(targetImgViewIndex - curImgViewIndex) === 1) {
        easeObj = Quint.easeOut;
        tweenTime = 1;
    } else {
        easeObj = Linear.easeNone;
        tweenTime = 0.15;
    }

    $imgs.each(function (i, item) {
        let pos = posArray[i];
        pos.angle = pos.angle + minusVal;  //(0.6301*0.06);
        let angleDistance = pos.angle * 100;
        let zPos = -Math.abs(angleDistance);
        let xPos = Math.sin(pos.angle) * radius;
        let imgAlpha = (Math.ceil(0.5 * totalImgToView) * imgMinus) * 100;

        imgAlpha = Math.abs(zPos) < imgAlpha ? 1 : 0;
        let rotDeg = Math.round(angleDistance) >= 0 ? -30 : 30;
        rotDeg = Math.round(angleDistance) === 0 ? 0 : rotDeg;


        TweenMax.to(item, tweenTime, {x: xPos, z: zPos, ease: easeObj, autoAlpha: imgAlpha, rotationY: rotDeg});

    });
    minusVal > 0 ? curImgViewIndex-- : curImgViewIndex++;

    if (curImgViewIndex === targetImgViewIndex) {
        clearInterval(curIntervalId);
    }


}

function showImgAt(index) {

    // add by me
    // $imgs = $('.container .img-holder');

    targetImgViewIndex = index;
    if (targetImgViewIndex === curImgViewIndex) {
        return;
    }
    clearInterval(curIntervalId);
    curIntervalId = setInterval(function () {
        rotate();
    }, 150);

    //update scrollbar
    if (!scrollbarDragging) {
        var l = $imgs.length - 1;
        if (targetImgViewIndex > l) {
            return;
        }
        var curScrollX = Math.abs(Math.round(targetImgViewIndex * (702 / l)));
        var tweenTime = Math.abs((targetImgViewIndex - curImgViewIndex) * 0.2);
        TweenMax.to($('.scroller'), tweenTime, {x: curScrollX, ease: Sine.easeOut});
    }
}

function setJump() {

    //CONTROLLER UPDATE
    var $input = $('.controller input');
    $input.keyup(function (e) {
        if (e.keyCode === 13) {
            showImgAt(parseInt($input.val()))
        }
    });
}



//just to do start up animation
//showImgAt(5);

function setDrag() {

    //----------------------- Dragging Utility ----------------------
    Draggable.create('.scroller', {
        type: 'x', bounds: {left: 0, top: 0, width: 802, height: 0}, onDrag: function () {
            var curImgIndex = Math.abs(Math.round(this.x / (802 / l)));

            targetImgViewIndex = curImgIndex;
            if (targetImgViewIndex === curImgViewIndex) {
                return;
            }
            rotate();

        }, onDragStart: function () {
            scrollbarDragging = true;
        }, onDragEnd: function (e) {
            scrollbarDragging = false;
        }
    });

}

function bindEvent() {
    $('.scrolller-container').on('click', function (e) {
        var curImgIndex = Math.abs(Math.round(e.offsetX / (802 / l)));
        if (curImgIndex >= $imgs.length) {
            curImgIndex = $imgs.length - 1;
        }
        console.log('boom');
        showImgAt(curImgIndex);
    });

    $('.scrolller-container .scroller').on('click', function (e) {
        e.stopPropagation();
    });


    $imgs.on('click', function () {
        showImgAt($imgs.index($(this)));
    });
}