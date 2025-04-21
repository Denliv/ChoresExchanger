import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function AddChorePage({ login, password }) {
  const navigateTo = useNavigate();
  const [choreName, setChoreName] = useState('');
  const [choreInfo, setChoreInfo] = useState('');
  const [imageFiles, setImageFiles] = useState([]);
  const [previewUrls, setPreviewUrls] = useState([]);

  const handleFileChanges = (e) => {
    const files = Array.from(e.target.files);
    setImageFiles(files);

    const previews = files.map(file => URL.createObjectURL(file));
    setPreviewUrls(previews);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append('userLogin', login);
    formData.append('userPassword', password);
    formData.append('name', choreName);
    formData.append('info', choreInfo);
    imageFiles.forEach((file, index) => {
      formData.append("images", file);
    });

    fetch('http://localhost:8080/addChore', {
      method: 'POST',
      body: formData,
    })
      .then((response) => {
        if (response.ok) {
          alert('Услуга добавлена');
          navigateTo('/main');
        } else {
          throw new Error('Ошибка добавления новой услуги');
        }
      })
      .catch((error) => {
        console.error('Ошибка:', error);
        alert('Ошибка добавления новой услуги');
      });
  };

  return (
    <div id="add-chore-page">
      <button id="secondary-button" onClick={() => navigateTo('/main')}>Назад</button>
      <h2>Добавление новой услуги</h2>
      <form id="add-chore-form" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Название (максимум 60 символов)"
          value={choreName}
          onChange={(e) => setChoreName(e.target.value)}
          maxLength="60"
          required
        />
        <textarea
          placeholder="Содержание (максимум 5000 символов)"
          value={choreInfo}
          onChange={(e) => setChoreInfo(e.target.value)}
          maxLength="5000"
          required
        />
        <div id="photo-input">
          <input
            style={{ border: '0' }}
            type="file"
            multiple
            accept="image/*"
            onChange={handleFileChanges}
          />
          <div className="image-preview" style={{ display: 'flex', gap: '10px', marginTop: '10px', flexWrap: 'wrap' }}>
            {previewUrls.map((url, index) => (
              <img key={index} src={url} alt={`preview-${index}`} style={{ width: '150px', height: '150px', objectFit: 'cover', borderRadius: '8px', border: '1px solid #ccc' }} />
            ))}
          </div>
        </div>
        <button id="secondary-button" type="submit">Опубликовать</button>
      </form>
    </div>
  );
}

export default AddChorePage;
