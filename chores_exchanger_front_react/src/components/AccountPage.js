import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function AccountPage({ login, password }) {
  const navigateTo = useNavigate();
  const [accountDetails, setAccountDetails] = useState({
    login: '',
    password: '',
    firstName: '',
    lastName: '',
    apartment: '',
  });

  useEffect(() => {
    fetch(`http://localhost:8080/getAccountDetails/${login}/${password}`)
    .then((response) => response.json())
    .then((data) => setAccountDetails(data))
    .catch((error) => console.error('Ошибка загрузки данных:', error));
  }, [login, password]);

  const handleLogOut = () => {
    navigateTo('/initial');
  };

  const handleDeleteAccount = () => {
    fetch('http://localhost:8080/deleteAccount', {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ login, password }),
    })
    .then((response) => {
      if (response.ok) {
        alert('Аккаунт удален');
        navigateTo('/initial');
      } else {
          throw new Error('Ошибка удаления аккаунта');
      }
    })
    .catch((error) => {
      console.error('Ошибка удаления аккаунта:', error);
      alert('Ошибка удаления аккаунта');
    });
  };

  return (
    <div id="account">
      <button id="secondary-button" onClick={() => navigateTo('/main')}>Назад</button>
      <table id="account-details">
        <tr>
          <td>Логин:</td>
          <td>{accountDetails.login}</td>
        </tr>
        <tr>
          <td>Пароль:</td>
          <td>{accountDetails.password}</td>
        </tr>
        <tr>
          <td>Имя:</td>
          <td>{accountDetails.firstName}</td>
        </tr>
        <tr>
          <td>Фамилия:</td>
          <td>{accountDetails.lastName}</td>
        </tr>
        <tr>
          <td>Квартира:</td>
          <td>{accountDetails.apartment}</td>
        </tr>
      </table>
      <button id="account-exit" onClick={handleLogOut}>
        Выйти из аккаунта
      </button>
      <button id="account-delete" onClick={handleDeleteAccount}>
        Удалить аккаунт
      </button>
    </div>
  );
}

export default AccountPage;