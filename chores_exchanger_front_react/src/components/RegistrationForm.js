import React from 'react';
import { useNavigate } from 'react-router-dom';

function RegistrationForm() {
  const navigateTo = useNavigate();
  const handleSubmit = (event) => {
    event.preventDefault();
    const formData = {
      firstName: document.getElementById('reg-name').value,
      lastName: document.getElementById('reg-surname').value,
      login: document.getElementById('reg-login').value,
      password: document.getElementById('reg-password').value,
      apartment: document.getElementById('reg-apartment').value
    };
    fetch('http://localhost:8080/addUser', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify(formData)
    })
    .then(response => {
      if (response.ok) {
          alert('Регистрация успешна!');
          navigateTo('/initial');
      } else {
          throw new Error('Ошибка регистрации');
      }
    })
    .catch(error => {
      console.error('Ошибка:', error);
      alert('Ошибка при регистрации');
    });
  };

  return (
    <div id="registration">
      <button id="secondary-button" onClick={() => navigateTo('/initial')}>Назад</button>
      <h1>Регистрация</h1>
      <form id="register-form" onSubmit={handleSubmit}>
        <input type="text" id="reg-login" placeholder="Логин" required/>
        <input type="password" id="reg-password" placeholder="Пароль" required/>
        <input type="text" id="reg-name" placeholder="Имя" required/>
        <input type="text" id="reg-surname" placeholder="Фамилия" required/>
        <input type="text" id="reg-apartment" placeholder="Квартира" required/>
        <button id="secondary-button" type="submit">Зарегистрировать</button>
      </form>
    </div>
  );
}

export default RegistrationForm;