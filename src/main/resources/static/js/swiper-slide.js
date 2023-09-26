const swiper = new Swiper('.swiper', {
    // Optional parameters
    autoplay: {
        delay: 3000,
        disableOnInteraction: false,
    },

    loop: true,

    // If we need pagination
    pagination: {
        el: '.swiper-pagination',
        clickable: true,
    },

    // Navigation arrows
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev',
    },

});