import './App.css';
import React, { useState } from "react";
import TourForm from "./components/TourForm";
import TourList from "./components/TourList";
//import TourDetails from "./components/TourDetails";
//import TourUpdate from "./components/TourUpdate";
import LogList from "./components/LogList";
import LogForm from "./components/LogForm";
//import LogUpdate from "./components/LogUpdate";
import LeafletMap from "./components/LeafletMap";
import 'leaflet/dist/leaflet.css';

const App = () => {
    const [tours, setTours] = useState([]);
    const [selectedTour, setSelectedTour] = useState(null);
    const [/*selectedTourId*/, setSelectedTourId] = useState(null);
    const [creatingTour, setCreatingTour] = useState(false);
    const [/*editingTour*/, setEditingTour] = useState(false);
    //const [activeTab, setActiveTab] = useState('details');
    //const [displayLogForm, setDisplayLogForm] = useState(false);
    //const [editingLog, setEditingLog] = useState(false);
    //const [selectedLog, setSelectedLog] = useState(null);
    const [logs, setLogs] = useState([]);
    //const [coordinates, setCoordinates] = useState([]);
    const [expandedTourIds, setExpandedTourIds] = useState([]); // Array für die IDs der ausgeklappten Touren

    // Funktion zum Erstellen einer neuen Tour
    const handleCreateTour = (newTour) => {
        setTours([...tours, newTour]);
        setCreatingTour(false);
    };

    const handleCreateLog = (newLog) => {
        setLogs([...logs, newLog]); // Füge den erstellten Log zur Liste hinzu
    };

    // Funktion zum Erstellen eines neuen Tour-Reports
    const handleCreateButtonClick = () => {
        setCreatingTour(prev => !prev); // Toggle creatingTour state
    };

    // Funktion zum Auswählen einer Tour
    const handleSelectTour = (tourId) => {
        setExpandedTourIds(prevIds => {
            if (prevIds.includes(tourId)) {
                return prevIds.filter(id => id !== tourId); // Tour einklappen
            } else {
                return [...prevIds, tourId]; // Tour ausklappen
            }
        });
        const selectedTour = tours.find(tour => tour.id === tourId);
        setSelectedTour(selectedTour); // Setze die ausgewählte Tour
    };

    // Funktion zum Löschen einer Tour
    const handleDeleteTour = (tourId) => {
        setTours(tours.filter(tour => tour.id !== tourId));
        setSelectedTour(null);
        setSelectedTourId(null);
        setExpandedTourIds(prevIds => prevIds.filter(id => id !== tourId)); // Entferne die gelöschte Tour von den ausgeklappten IDs
    };

    // Funktion zum Aktualisieren einer Tour
    const handleUpdateTour = (updatedTour) => {
        setTours(tours.map(tour => (tour.id === updatedTour.id ? updatedTour : tour)));
        setSelectedTour(updatedTour);
        setEditingTour(false);
    };
/*
    // Funktion zum Bearbeiten einer Tour
    const handleEditTour = () => {
        setEditingTour(true);
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
    };

    let displayComponent;
    if (creatingTour) {
        displayComponent = <TourForm onCreateTour={handleCreateTour} />;
    } else if (editingTour) {
        displayComponent = <TourUpdate tour={selectedTour} onUpdateTour={handleUpdateTour} onCancel={handleCancelUpdate} />;
    } else if (selectedTour) {
        if (activeTab === 'details') {
            displayComponent = <TourDetails tour={selectedTour} onDelete={() => handleDeleteTour(selectedTour.id)} onEdit={handleEditTour} />;
        } else if (activeTab === 'logs') {
            if (displayLogForm) {
                displayComponent = <LogForm tourId={selectedTour.id} />;
            } else if (editingLog && selectedLog) {
                displayComponent = <LogUpdate log={selectedLog} onCancelLog={handleCancelUpdateLog} onUpdateLog={handleUpdateLog} />;
            } else {
                displayComponent = <LogList tourId={selectedTour.id} onDisplayLogForm={handleCreateLogButtonClick} onEditLog={handleEditLog} />;
            }
        }
    }*/

    return (
        <div>
            <div className="app-container">
                <div className="tour-list">
                    <h1>Tourplanner</h1>
                    <TourList
                        tours={tours} // Alle Touren übergeben
                        expandedTourIds={expandedTourIds} // Alle ausgeklappten Tour-IDs
                        onSelectTour={handleSelectTour} // Funktion für die Auswahl
                        onDeleteTour={handleDeleteTour} // Funktion für das Löschen
                        onUpdateTour={handleUpdateTour} // Funktion für das Aktualisieren
                    />
                <button onClick={handleCreateButtonClick}>
                    {creatingTour ? 'Hide Create Tour Form' : 'Create Tour'}
                </button>
                {creatingTour && <TourForm onCreateTour={handleCreateTour} />}
                </div>
                {selectedTour && (
                    <div>
                        <LogForm tourId={selectedTour.id} onCreateLog={handleCreateLog} />
                        <LogList tourId={selectedTour.id} />
                    </div>
                )}
                <div className="map-container">
                    <LeafletMap />
                </div>
            </div>


        </div>
    );
};

export default App;
