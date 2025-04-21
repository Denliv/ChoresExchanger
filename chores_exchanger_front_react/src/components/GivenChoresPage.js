import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function GivenChoresPage({ showModal, login, password, fetchChores }) {
  const navigateTo = useNavigate();
  const [takenChores, setTakenChores] = useState([]);
  const [notTakenChores, setNotTakenChores] = useState([]);

  useEffect(() => {
    fetchChores(setTakenChores, 'getGivenPageChoresTaken', login, password);
    fetchChores(setNotTakenChores, 'getGivenPageChoresNotTaken', login, password);
  }, [login, password, fetchChores]);

  const handleChoreInfoClick = (choreId) => {
    showModal(choreId);
  };

  return (
    <div id="given-chores">
      <button id="secondary-button" onClick={() => navigateTo('/main')}>Назад</button>
      <div id="given-chores-content">
        <h2>Выданные услуги</h2>
        <div id="given-chores-list-blocks">
          <div id="given-chores-list-block-taken">
            <h3>Принятые услуги</h3>
            <div id="given-chores-list-taken">
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
          <div id="given-chores-list-block-not-taken">
            <h3>Непринятые услуги</h3>
            <div id="given-chores-list-not-taken">
              {notTakenChores.length === 0
              ? <p>Пусто</p>
              : notTakenChores.map((chore) => (
                <div key={chore.id}>
                  <span>{chore.name}</span>
                  <button id="secondary-button" onClick={() => handleChoreInfoClick(chore.id)}>Info</button>
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default GivenChoresPage;