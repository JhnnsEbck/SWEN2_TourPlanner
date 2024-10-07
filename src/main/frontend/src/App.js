import './App.css';
import React, { useState } from "react";
import LeafletMap from "./components/LeafletMap";
import 'leaflet/dist/leaflet.css';

import TourForm from "./components/TourForm";
import TourList from "./components/TourList";
import LogList from "./components/LogList";
import LogForm from "./components/LogForm";
//import LogUpdate from "./components/LogUpdate";
//import TourDetails from "./components/TourDetails";
//import TourUpdate from "./components/TourUpdate";


const App = () => {
    const [tours, setTours] = useState([]);
    const [selectedTour, setSelectedTour] = useState(null);
    const [setSelectedTourId] = useState(null);
    const [creatingTour, setCreatingTour] = useState(false);
    const [setEditingTour] = useState(false);
    const [logs, setLogs] = useState([]);
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
