document.addEventListener('click', function (e) {
    var gatilho = e.target.closest('a.expansor');
    if (!gatilho) return;

    e.preventDefault();
    var alvo = document.getElementById(gatilho.getAttribute('data-alvo'));
    if (!alvo) return;

    var abrir = alvo.hasAttribute('hidden');
    alvo.toggleAttribute('hidden', !abrir);
    gatilho.classList.toggle('aberto', abrir);

    document.querySelectorAll('nav a').forEach(function (link) {
        if (link.pathname === location.pathname) link.classList.add('ativo');
    });

});
