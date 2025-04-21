import React, { useState } from 'react';
import { HashRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import InitialButtons from './components/InitialButtons';
import RegistrationForm from './components/RegistrationForm';
import LoginForm from './components/LoginForm';
import MainPage from './components/MainPage';
import AccountPage from './components/AccountPage';
import AddChorePage from './components/AddChorePage';
import GivenChoresPage from './components/GivenChoresPage';
import TakenChoresPage from './components/TakenChoresPage';
import CompletedChoresPage from './components/CompletedChoresPage';
import { ChoreInfo, ChoreButtons } from './components/ChoreInfo';
import './App.css';
import { Button, Modal } from 'antd';

function App() {
  const [login, setLogin] = useState(null);
  const [password, setPassword] = useState(null);
  const [username, setUsername] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState(null);
  const [footerButtons, setFooterButtons] = useState(null);

  const fetchChores = (setChoresList, endpoint, login, password) => {
    fetch(`http://localhost:8080/${endpoint}/${login}/${password}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Ошибка получения списка услуг');
        }
    })
    .then((data) => setChoresList(data.list))
    .catch(error => {
      console.error('Ошибка:', error);
      alert('Ошибка при загрузке списка услуг');
    });
  }

  const showModal = (choreId) => {
    setIsModalOpen(true);
    setModalContent(
      <ChoreInfo selectedChoreId={choreId} login={login} />
    );
    setFooterButtons(
      <ChoreButtons selectedChoreId={choreId} login={login} />
    );
  };
  const handleOk = () => {
    setIsModalOpen(false);
  };
  const handleCancel = () => {
    setIsModalOpen(false);
  };

  return (
    <Router>
      <div className="container">
        <Routes>
          <Route path="/" element={<InitialButtons />} />
          <Route path="/initial" element={<InitialButtons />} />
          <Route path="/registration" element={<RegistrationForm />} />
          <Route path="/login" element={<LoginForm setLogin={setLogin} setPassword={setPassword} setUsername={setUsername} />} />
          <Route path="/main" element={<MainPage showModal={showModal} login={login} password={password} username={username} fetchChores={fetchChores} />} />
          <Route path="/account" element={<AccountPage login={login} password={password} />} />
          <Route path="/addChore" element={<AddChorePage login={login} password={password} />} />
          <Route path="/givenChores" element={<GivenChoresPage showModal={showModal} login={login} password={password} fetchChores={fetchChores} />} />
          <Route path="/takenChores" element={<TakenChoresPage showModal={showModal} login={login} password={password} fetchChores={fetchChores} />} />
          <Route path="/completedChores" element={<CompletedChoresPage showModal={showModal} login={login} password={password} fetchChores={fetchChores} />} />
        </Routes>
        <Modal
        title="Информация об услуге"
        open={isModalOpen}
        onOk={handleOk}
        onCancel={handleCancel}
        maskClosable={false}
        footer={footerButtons}
        >
          {modalContent}
        </Modal>
      </div>
    </Router>
  );
}

export default App;

/*
import React, { useState } from 'react';
import InitialButtons from './components/InitialButtons';
import RegistrationForm from './components/RegistrationForm';
import LoginForm from './components/LoginForm';
import MainPage from './components/MainPage';
import AccountPage from './components/AccountPage';
import AddChorePage from './components/AddChorePage';
import GivenChoresPage from './components/GivenChoresPage';
import TakenChoresPage from './components/TakenChoresPage';
import CompletedChoresPage from './components/CompletedChoresPage';
import ChoreInfo from './components/ChoreInfo';
import './App.css';

function App() {
  const [currentPage, setCurrentPage] = useState('initial');
  const [login, setLogin] = useState(null);
  const [password, setPassword] = useState(null);
  const [username, setUsername] = useState(null);
  const [selectedChoreId, setSelectedChoreId] = useState(null);
  const [navigateToRoute, setNavigateToRoute] = useState(null);

  const navigateTo = (page) => {
    setCurrentPage(page);
  };

  const fetchChores = (setChoresList, endpoint, login, password) => {
    fetch(`http://localhost:8080/${endpoint}/${login}/${password}`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json'
      }
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Ошибка получения списка услуг');
        }
    })
    .then((data) => setChoresList(data.list))
    .catch(error => {
      console.error('Ошибка:', error);
      alert('Ошибка при загрузке списка услуг');
    });
  }

  return (
    <div className="container">
      {currentPage === 'initial' && <InitialButtons navigateTo={navigateTo} />}
      {currentPage === 'registration' && <RegistrationForm navigateTo={navigateTo} />}
      {currentPage === 'login' && <LoginForm navigateTo={navigateTo} setLogin={setLogin} setPassword={setPassword} setUsername={setUsername} />}
      {currentPage === 'main' && <MainPage navigateTo={navigateTo} setNavigateToRoute={setNavigateToRoute} login={login} password={password}  username={username} fetchChores={fetchChores} setSelectedChoreId={setSelectedChoreId} />}
      {currentPage === 'account' && <AccountPage navigateTo={navigateTo} login={login} password={password} />}
      {currentPage === 'addChore' && <AddChorePage navigateTo={navigateTo} login={login} password={password} />}
      {currentPage === 'givenChores' && <GivenChoresPage navigateTo={navigateTo} setNavigateToRoute={setNavigateToRoute} login={login} password={password} fetchChores={fetchChores} setSelectedChoreId={setSelectedChoreId} />}
      {currentPage === 'takenChores' && <TakenChoresPage navigateTo={navigateTo} setNavigateToRoute={setNavigateToRoute} login={login} password={password} fetchChores={fetchChores} setSelectedChoreId={setSelectedChoreId} />}
      {currentPage === 'completedChores' && <CompletedChoresPage navigateTo={navigateTo} setNavigateToRoute={setNavigateToRoute} login={login} password={password} fetchChores={fetchChores} setSelectedChoreId={setSelectedChoreId} />}
      {currentPage === 'choreInfo' && <ChoreInfo navigateTo={navigateTo} navigateToRoute={navigateToRoute} selectedChoreId={selectedChoreId} login={login} />}
    </div>
  );
}

export default App;
*/