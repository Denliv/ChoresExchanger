import React, { useState, useEffect } from 'react';

function ChoreInfo({ selectedChoreId, login }) {
  const [chore, setChore] = useState(null);
  
  const fetchChoreData = async () => {
    if (!selectedChoreId) return;

    try {
      const response = await fetch(`http://localhost:8080/chore/${selectedChoreId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      
      if (!response.ok) {
        throw new Error('Ошибка получения информации об услуге');
      }
      
      const data = await response.json();
      setChore(data);
    } catch(error) {
      console.error('Ошибка:', error);
    }
  };

  useEffect(() => {
    if (selectedChoreId) {
      fetchChoreData();
    }
  }, [selectedChoreId]);

  return (
    !chore
    ? <div>Загрузка...</div>
    : <div className="chore-info-dialog">
      <h2>{chore.name}</h2>
      <p><strong>Описание:</strong> {chore.info}</p>
      <p><strong>Статус:</strong> {chore.statusInfo}</p>
      <p><strong>Заказчик:</strong> {chore.userFirstName} {chore.userLastName}, кв. {chore.userApartment}</p>
      {chore.images && chore.images.length > 0 && (
          <div style={{ display: 'flex', gap: '10px', marginTop: '10px', flexWrap: 'wrap' }}>
            {chore.images.map((imgBase64, index) => (
              <img
                key={index}
                src={`data:image/jpeg;base64,${imgBase64}`}
                alt={`Изображение ${index + 1}`}
                style={{ width: '150px', height: '150px', objectFit: 'cover', borderRadius: '8px', border: '1px solid #ccc' }}
                // style={{ maxWidth: '100%', maxHeight: '300px', borderRadius: '8px', marginTop: '16px', border: '1px solid #ccc' }}
              />
            ))}
          </div>
        )}
    </div>
  );
}

function ChoreButtons({ selectedChoreId, login }) {
  const [chore, setChore] = useState(null);
  
  const fetchChoreData = async () => {
    if (!selectedChoreId) return;

    try {
      const response = await fetch(`http://localhost:8080/chore/${selectedChoreId}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });
      
      if (!response.ok) {
        throw new Error('Ошибка получения информации об услуге');
      }
      
      const data = await response.json();
      setChore(data);
    } catch(error) {
      console.error('Ошибка:', error);
    }
  };

  useEffect(() => {
    if (selectedChoreId) {
      fetchChoreData();
    }
  }, [selectedChoreId]);

  const handleAction = async (action) => {
    try {
      let endpoint = '';
      let method = 'PUT';
      let message = "";

      switch (action) {
        case 'take':
          endpoint = 'takeChore';
          message = "Услуга принята";
          break;
        case 'refuse':
          endpoint = 'refuseChore';
          message = "Отказ от услуги принят";
          break;
        case 'delete':
          endpoint = 'deleteChore';
          message = "Услуга удалена";
          method = 'DELETE';
          break;
        case 'complete':
          endpoint = 'completeChore';
          message = "Услуга завершена";
          break;
        default:
          return;
      }

      const response = await fetch(`http://localhost:8080/${endpoint}`, {
        method: method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          userLogin: login,
          choreId: selectedChoreId
        })
      });
      
      if (!response.ok) {
        throw new Error('Ошибка выполнения действия');
      }
      
      alert(message);
    } catch(error) {
      console.error('Ошибка:', error);
      alert(error.message);
    }
  };

  return (
    !chore
    ? <div></div>
    : <div className="action-buttons">
        {chore.statusName === 'CREATED' && chore.userLogin !== login && (
          <button id="secondary-button" onClick={() => handleAction('take')}>Принять услугу</button>
        )}
        {chore.statusName === 'CREATED' && chore.userLogin === login && (
          <button id="secondary-button" onClick={() => handleAction('delete')}>Удалить услугу</button>
        )}
        {chore.statusName === 'TAKEN' && chore.userLogin !== login && (
          <button id="secondary-button" onClick={() => handleAction('refuse')}>Отказаться</button>
        )}
        {chore.statusName === 'TAKEN' && chore.userLogin === login && (
          <button id="secondary-button" onClick={() => handleAction('complete')}>Завершить</button>
        )}
      </div>
  );
}

export { ChoreInfo, ChoreButtons };
