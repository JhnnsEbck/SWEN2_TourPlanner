import './App.css';
import React, { useState } from "react";
import LeafletMap from "./components/LeafletMap";
import TourForm from "./components/TourForm";
import TourList from "./components/TourList";
import TourDetails from "./components/TourDetails";
import TourUpdate from "./components/TourUpdate";
import LogList from "./components/LogList";
import LogForm from "./components/LogForm";
import LogUpdate from "./components/LogUpdate";

const App = () => {
    const [tours, setTours] = useState([]);
    const [selectedTour, setSelectedTour] = useState(null);
    const [selectedTourId, setSelectedTourId] = useState(null);
    const [creatingTour, setCreatingTour] = useState(false);
    const [editingTour, setEditingTour] = useState(false);
    const [activeTab, setActiveTab] = useState('details');
    const [displayLogForm, setDisplayLogForm] = useState(false);
    const [editingLog, setEditingLog] = useState(false);
    const [selectedLog, setSelectedLog] = useState(null);
    const [logs, setLogs] = useState([]);

    const handleCreateTour = (newTour) => {
        setTours([...tours, newTour]);
        setCreatingTour(false);
    };

    const handleSelectTour = (tour) => {
        setSelectedTour(tour);
        setSelectedTourId(tour.id);
        setCreatingTour(false);
        setActiveTab('details');
    };

    const handleCreateButtonClick = () => {
        setSelectedTour(null);
        setSelectedTourId(null);
        setCreatingTour(true);
        setActiveTab('details');
    };

    const handleDeleteTour = (tourId) => {
        setTours(tours.filter(tour => tour.id !== tourId));
        setSelectedTour(null);
        setSelectedTourId(null);
    };

    const handleEditTour = () => {
        setEditingTour(true);
    };

    const handleUpdateTour = (updatedTour) => {
        setTours(tours.map(tour => (tour.id === updatedTour.id ? updatedTour : tour)));
        setSelectedTour(updatedTour);
        setEditingTour(false);
    };

    const handleCancelUpdate = () => {
        setEditingTour(false);
    };

    const handleCreateLogButtonClick = () => {
        setDisplayLogForm(true);
    };

    const handleEditLog = (log) => {
        setEditingLog(true);
        setSelectedLog(log);
    };

    const handleCancelUpdateLog = () => {
        setEditingLog(false);
        setSelectedLog(null);
    };

    const handleUpdateLog = (updatedLog) => {
        setLogs(logs.map(log => (log.id === updatedLog.id ? updatedLog : log)));
        setEditingLog(false);
        setSelectedLog(null);
    }

    let displayComponent;
    if (creatingTour) {
        displayComponent = <TourForm onCreateTour={handleCreateTour} />;
    } else if (editingTour) {
        displayComponent = <TourUpdate tour={selectedTour} onUpdateTour={handleUpdateTour} onCancel={handleCancelUpdate} />;
    } else if (selectedTour) {
        if (activeTab === 'details') {
            displayComponent = <TourDetails tour={selectedTour} onDelete={() => handleDeleteTour(selectedTour.id)} onEdit={handleEditTour} />;
        } else if (activeTab === 'logs') {
            if(displayLogForm){
                displayComponent = <LogForm tourId={selectedTour.id} />
            } else if(editingLog && selectedLog){
                displayComponent = <LogUpdate log={selectedLog} onCancelLog={handleCancelUpdateLog} onUpdateLog={handleUpdateLog}/>
            }
            else{
                displayComponent = <LogList tourId={selectedTour.id} onDisplayLogForm={handleCreateLogButtonClick} onEditLog={handleEditLog} />;
            }
        }
    }

    return (
        <div>
            <h1>Tourplanner</h1>
            <div className="app-container">
                <div className="tour-list">
                    <TourList
                        tours={tours}
                        selectedTourId={selectedTourId}
                        onSelectTour={handleSelectTour}
                        onCreateTour={handleCreateButtonClick}
                    />
                </div>
                <div className="display-component">
                    <ul className="tab-list">
                        <li className={activeTab === 'details' ? 'active' : ''} onClick={() => setActiveTab('details')}>Route Details</li>
                        <li className={activeTab === 'logs' ? 'active' : ''} onClick={() => setActiveTab('logs') + setDisplayLogForm(false)}>Tour Logs</li>
                    </ul>
                    {displayComponent}
                </div>
            </div>
        </div>
    );
};

export default App;
