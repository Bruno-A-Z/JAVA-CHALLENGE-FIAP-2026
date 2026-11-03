// Marca no menu a página em que o usuário está
(function () {
    var links = document.querySelectorAll('.lateral nav a');
    if (!links.length) return;

    var atual = location.pathname.replace(/\/$/, '');
    var inicio = links[0].pathname.replace(/\/$/, '');

    links.forEach(function (link) {
        var caminho = link.pathname.replace(/\/$/, '');
        // "Início" só vale na própria raiz; os demais valem também nas subpáginas (/novo, /editar...)
        var ativo = caminho === inicio
            ? atual === caminho
            : atual === caminho || atual.indexOf(caminho + '/') === 0;

        if (ativo) {
            link.classList.add('ativo');
            link.setAttribute('aria-current', 'page');
        }
    });
})();

// Abre e fecha as linhas de detalhe das tabelas (pets do tutor, agenda do veterinário...)
document.addEventListener('click', function (e) {
    var gatilho = e.target.closest('a.expansor');
    if (!gatilho) return;

    e.preventDefault();
    var alvo = document.getElementById(gatilho.getAttribute('data-alvo'));
    if (!alvo) return;

    var abrir = alvo.hasAttribute('hidden');
    alvo.toggleAttribute('hidden', !abrir);
    gatilho.classList.toggle('aberto', abrir);
    gatilho.setAttribute('aria-expanded', abrir ? 'true' : 'false');
});
