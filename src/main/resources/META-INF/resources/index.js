const token = sessionStorage.getItem('token');
if (!token) {
    // Redirige al usuario a la página de inicio de sesión si no está autenticado
    window.location.href = 'login.html';
}
