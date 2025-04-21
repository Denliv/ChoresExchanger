import React from 'react';
import { useNavigate } from 'react-router-dom';

function InitialButtons() {
  const navigateTo = useNavigate();

  return (
    <div id="initial-buttons">
      <button onClick={() => navigateTo('/registration')}>Регистрация</button>
      <button onClick={() => navigateTo('/login')}>Вход</button>
    </div>
  );
}

export default InitialButtons;