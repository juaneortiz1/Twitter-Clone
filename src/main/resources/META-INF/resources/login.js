// META-INF/resources/login.js
import { Amplify, Auth } from 'https://cdn.jsdelivr.net/npm/aws-amplify@4.3.19/dist/aws-amplify.min.js';
import { awsConfig } from './aws-exports.js';
Amplify.configure(awsConfig);

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
        const user = await Auth.signIn(username, password);
        console.log('Login successful!', user);

        // Almacenar el token en sessionStorage
        sessionStorage.setItem('token', user.signInUserSession.idToken.jwtToken);
        sessionStorage.setItem('username', user.username);

        document.getElementById('loginMessage').textContent = 'Login successful!';
        document.getElementById('loginMessage').style.color = 'green';
        window.location.href = 'index.html';
    } catch (error) {
        console.error('Error:', error);
        document.getElementById('loginMessage').textContent = 'Invalid username or password';
        document.getElementById('loginMessage').style.color = 'red';
    }
}

async function registerUser(event) {
    event.preventDefault();
    const username = document.getElementById('signupUsername').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;

    try {
        const { user } = await Auth.signUp({
            username,
            password,
            attributes: {
                email
            }
        });
        console.log('Registration successful!', user);

        signupMessage.textContent = 'Registration successful!';
        signupMessage.style.color = 'green';
    } catch (error) {
        console.error('Error:', error);
        signupMessage.textContent = 'Registration failed';
        signupMessage.style.color = 'red';
    }
}
