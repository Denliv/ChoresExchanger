import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function MainPage({ showModal, login, password, username, fetchChores }) {
  const navigateTo = useNavigate();
  const [mainChores, setMainChores] = useState([]);

  useEffect(() => {
    fetchChores(setMainChores, 'getMainPageChores', login, password);
  }, [login, password, fetchChores]);

  const handleChoreInfoClick = (choreId) => {
    showModal(choreId);
  };

  return (
    <div id="main-page">
      <div className="header">
        <div id="main-buttons">
          <button onClick={() => navigateTo('/addChore')}>Добавить услугу</button>
          <button onClick={() => navigateTo('/givenChores')}>Выданные услуги</button>
          <button onClick={() => navigateTo('/takenChores')}>Принятые услуги</button>
          <button onClick={() => navigateTo('/completedChores')}>Выполненные услуги</button>
        </div>
        <div id="main-account">
          <div id="main-account-info">{username}</div>
          <button onClick={() => navigateTo('/account')}>Личный кабинет</button>
        </div>
      </div>
      <div className="main-content">
        <h2>Список услуг</h2>
        <div id="main-chores-list">
          {mainChores.length === 0
          ? <p>Пусто</p>
          : mainChores.map((chore) => (
            <div key={chore.id}>
              <span>{chore.name}</span>
              <button id="secondary-button" onClick={() => handleChoreInfoClick(chore.id)}>Info</button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default MainPage;
