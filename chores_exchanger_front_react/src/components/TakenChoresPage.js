import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function TakenChoresPage({ showModal, login, password, fetchChores }) {
  const navigateTo = useNavigate();
  const [takenChores, setTakenChores] = useState([]);

  useEffect(() => {
    fetchChores(setTakenChores, 'getTakenPageChores', login, password);
  }, [login, password, fetchChores]);

  const handleChoreInfoClick = (choreId) => {
    showModal(choreId);
  };

  return (
    <div id="taken-chores">
      <button id="secondary-button" onClick={() => navigateTo('/main')}>Назад</button>
      <div id="taken-chores-content">
        <h2>Принятые услуги</h2>
        <div id="taken-chores-list">
          {takenChores.length === 0
          ? <p>Пусто</p>
          : takenChores.map((chore) => (
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

export default TakenChoresPage;