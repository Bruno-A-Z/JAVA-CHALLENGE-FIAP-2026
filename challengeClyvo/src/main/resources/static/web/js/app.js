// Expande/recolhe as linhas de detalhe das listagens
document.addEventListener('click', function (e) {
    var gatilho = e.target.closest('a.expansor');
    if (!gatilho) return;

    e.preventDefault();
    var alvo = document.getElementById(gatilho.getAttribute('data-alvo'));
    if (!alvo) return;

    var abrir = alvo.hasAttribute('hidden');
    alvo.toggleAttribute('hidden', !abrir);
    gatilho.classList.toggle('aberto', abrir);
});

// Destaca no menu o link da página atual
document.querySelectorAll('nav a').forEach(function (link) {
    if (link.pathname === location.pathname) link.classList.add('ativo');
});

// Evita duplo envio: desabilita o botão enquanto o formulário é enviado
document.addEventListener('submit', function (event) {
    if (event.defaultPrevented) return;

    var form = event.target;
    if (form.dataset.enviando) {
        event.preventDefault();
        return;
    }

    form.dataset.enviando = 'true';
    var botao = form.querySelector('button[type="submit"], button:not([type])');
    if (botao) botao.disabled = true;
});

// Ao voltar pelo botão do navegador, libera os formulários novamente
window.addEventListener('pageshow', function () {
    document.querySelectorAll('form[data-enviando]').forEach(function (form) {
        delete form.dataset.enviando;
        form.querySelectorAll('button:disabled').forEach(function (botao) {
            botao.disabled = false;
        });
    });
});