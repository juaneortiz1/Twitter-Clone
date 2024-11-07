// Check if user is logged in
const userId = sessionStorage.getItem('userId');
const username = sessionStorage.getItem('username');

if (!userId || !username) {
    window.location.href = 'login.html';
}

// Set username in header
document.getElementById('username').textContent = username;

// Cache para almacenar usernames
const usernameCache = new Map();

// Logout function
function logout() {
    sessionStorage.removeItem('userId');
    sessionStorage.removeItem('username');
    window.location.href = 'login.html';
}

// Función para obtener username
async function getUserUsername(userId) {
    // Si ya tenemos el username en cache, lo devolvemos
    if (usernameCache.has(userId)) {
        return usernameCache.get(userId);
    }

    try {
        const response = await fetch(`http://localhost:8080/api/users/${userId}`);
        if (!response.ok) throw new Error('Failed to fetch user');

        const userData = await response.json();
        // Guardamos en cache para futuras referencias
        usernameCache.set(userId, userData.username);
        return userData.username;
    } catch (error) {
        console.error('Error fetching username:', error);
        return `User ${userId}`; // Fallback en caso de error
    }
}

// Load all streams and their posts
async function loadStreams() {
    try {
        const response = await fetch('http://localhost:8080/api/streams');
        if (!response.ok) throw new Error('Failed to fetch streams');

        const streams = await response.json();
        const container = document.getElementById('streamsContainer');
        container.innerHTML = ''; // Clear existing streams

        for (const stream of streams) {
            const streamElement = createStreamElement(stream);
            container.appendChild(streamElement);
            await loadStreamPosts(stream.id);
        }
    } catch (error) {
        console.error('Error loading streams:', error);
    }
}

// Create stream element
function createStreamElement(stream) {
    const streamDiv = document.createElement('div');
    streamDiv.className = 'stream-card';
    streamDiv.innerHTML = `
        <div class="stream-header">
            <h3 class="stream-title">${stream.name}</h3>
        </div>
        <div class="posts-container" id="posts-${stream.id}">
            <!-- Posts will be loaded here -->
        </div>
        <form onsubmit="createPost(event, ${stream.id})" class="new-post-form">
            <input type="text" class="new-post-input" placeholder="Write a post..." required>
            <button type="submit" class="submit-post">Post</button>
        </form>
    `;
    return streamDiv;
}

// Load posts for a specific stream
async function loadStreamPosts(streamId) {
    try {
        const response = await fetch(`http://localhost:8080/api/posts/stream/${streamId}`);
        if (!response.ok) {
            if (response.status === 404) {
                // No posts yet, that's okay
                return;
            }
            throw new Error('Failed to fetch posts');
        }

        const posts = await response.json();
        const postsContainer = document.getElementById(`posts-${streamId}`);
        postsContainer.innerHTML = ''; // Clear existing posts

        for (const post of posts) {
            const postElement = await createPostElement(post);
            postsContainer.appendChild(postElement);
        }
    } catch (error) {
        console.error(`Error loading posts for stream ${streamId}:`, error);
    }
}

// Create post element
async function createPostElement(post) {
    const postDiv = document.createElement('div');
    postDiv.className = 'post';

    const timestamp = new Date(post.timestamp).toLocaleString();
    const authorUsername = post.authorId === parseInt(userId)
        ? 'You'
        : await getUserUsername(post.authorId);

    postDiv.innerHTML = `
        <div class="post-header">
            <span>Posted by: ${authorUsername}</span>
            <span>${timestamp}</span>
        </div>
        <div class="post-content">
            ${post.content}
        </div>
    `;
    return postDiv;
}

// Create new post
async function createPost(event, streamId) {
    event.preventDefault();
    const input = event.target.querySelector('input');
    const content = input.value;

    try {
        const response = await fetch('http://localhost:8080/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                content: content,
                authorId: parseInt(userId),
                streamId: streamId,
                timestamp: new Date().toISOString()
            })
        });

        if (!response.ok) throw new Error('Failed to create post');

        input.value = ''; // Clear input
        await loadStreamPosts(streamId); // Reload posts for this stream
    } catch (error) {
        console.error('Error creating post:', error);
        alert('Failed to create post. Please try again.');
    }
}

// Create new stream
document.getElementById('createStreamForm').addEventListener('submit', async (event) => {
    event.preventDefault();
    const input = document.getElementById('newStreamName');
    const name = input.value;

    try {
        const response = await fetch('http://localhost:8080/api/streams', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                posts: []
            })
        });

        if (!response.ok) throw new Error('Failed to create stream');

        input.value = ''; // Clear input
        await loadStreams(); // Reload all streams
    } catch (error) {
        console.error('Error creating stream:', error);
        alert('Failed to create stream. Please try again.');
    }
});

// Initial load of streams
loadStreams();