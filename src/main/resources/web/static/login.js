const signInForm = document.getElementById('signInForm');
const signUpForm = document.getElementById('signUpForm');
const loginMessage = document.getElementById('loginMessage');
const signupMessage = document.getElementById('signupMessage');

function showSignIn() {
    signUpForm.classList.add('hidden');
    signInForm.classList.remove('hidden');
}

function showSignUp() {
    signInForm.classList.add('hidden');
    signUpForm.classList.remove('hidden');
}

async function loginUser(event) {
    event.preventDefault();
    const username = document.getElementById('loginUsername').value;
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch('http://localhost:8080/api/users/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json(); // Suponiendo que el backend devuelve un objeto con el id del usuario
            // Almacenar el ID de usuario en sessionStorage
            sessionStorage.setItem('userId', data.id); // Usamos el 'id' que devuelve la respuesta del servidor
            sessionStorage.setItem('username', data.username); // También puedes guardar el nombre de usuario si lo deseas

            // Redirigir al usuario a la página principal después del inicio de sesión

            document.getElementById('loginMessage').textContent = 'Login successful!';
            document.getElementById('loginMessage').style.color = 'green';
            window.location.href = 'index.html';  // Redirige a la página principal después de iniciar sesión
        } else {
            document.getElementById('loginMessage').textContent = 'Invalid username or password';
            document.getElementById('loginMessage').style.color = 'red';
        }
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('loginMessage').textContent = 'Login failed';
    }
}



async function registerUser(event) {
    event.preventDefault();
    const username = document.getElementById('signupUsername').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;

    try {
        const response = await fetch('http://localhost:8080/api/users', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, email, password })
        });

        if (response.status === 201) {
            signupMessage.textContent = 'Registration successful!';
            signupMessage.style.color = 'green';
        } else {
            signupMessage.textContent = 'Registration failed';
            signupMessage.style.color = 'red';
        }
    } catch (error) {
        console.error('Error:', error);
        signupMessage.textContent = 'Registration failed';
    }
}
