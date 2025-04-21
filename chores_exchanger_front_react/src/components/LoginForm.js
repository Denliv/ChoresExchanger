import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginForm({ setLogin, setPassword, setUsername }) {
  const navigateTo = useNavigate();
  const [formLogin, setFormLogin] = useState('');
  const [formPassword, setFormPassword] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    fetch('http://localhost:8080/signIn', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        login: formLogin,
        password: formPassword,
      }),
    })
    .then((response) => {
      if (response.ok) {
        return response.json();
      } else {
        throw new Error('Неверный логин или пароль');
      }
    })
    .then((data) => {
      setLogin(data.login);
      setPassword(data.password);
      setUsername(data.firstName + " " + data.lastName);
      navigateTo('/main');
    })
    .catch((error) => {
      console.error('Ошибка:', error);
      alert('Неверный логин или пароль');
    });
  };

  return (
    <div id="login">
      <button id="secondary-button" onClick={() => navigateTo('/initial')}>Назад</button>
      <h1>Вход</h1>
      <form id="login-form" onSubmit={handleSubmit}>
        <input type="text" placeholder="Логин" value={formLogin} onChange={(e) => setFormLogin(e.target.value)} required/>
        <input type="password" placeholder="Пароль" value={formPassword} onChange={(e) => setFormPassword(e.target.value)} required/>
        <button id="secondary-button" type="submit">Войти</button>
      </form>
    </div>
  );
}

export default LoginForm;