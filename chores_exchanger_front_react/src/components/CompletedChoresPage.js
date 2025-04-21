import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function CompletedChoresPage({ showModal, login, password, fetchChores }) {
  const navigateTo = useNavigate();
  const [completedChores, setCompletedChores] = useState([]);

  useEffect(() => {
    fetchChores(setCompletedChores, 'getCompletedPageChores', login, password);
  }, [login, password, fetchChores]);

  const handleChoreInfoClick = (choreId) => {
    showModal(choreId);
  };

  return (
    <div id="completed-chores">
      <button id="secondary-button" onClick={() => navigateTo('/main')}>Назад</button>
      <div id="completed-chores-content">
        <h2>Выполненные услуги</h2>
        <div id="completed-chores-list">
          {completedChores.length === 0
          ? <p>Пусто</p>
          : completedChores.map((chore) => (
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

export default CompletedChoresPage;