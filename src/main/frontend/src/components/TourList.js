import React, { useEffect, useState } from "react";
import axios from "axios";
import TourDetails from "./TourDetails"; // Importieren von TourDetails
import LogList from "./LogList"; // Importieren von LogList
import TourUpdate from "./TourUpdate"; // Importieren von TourUpdate

const TourList = ({ expandedTourIds, onSelectTour, onUpdateTour }) => {
    const [tours, setTours] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [searchTerm, setSearchTerm] = useState('');
    const [editingTourId, setEditingTourId] = useState(null); // ID der Tour, die bearbeitet wird

    useEffect(() => {
        const fetchTours = async () => {
            try {
                const response = await axios.get('/tour');  // API Call für Touren
                setTours(response.data);
                setLoading(false);
            } catch (err) {
                setError(err.message);
                setLoading(false);
            }
        };

        fetchTours();
    }, []);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>Error: {error}</div>;

    // Filtere Touren basierend auf dem Suchbegriff
    const filteredTours = tours.filter(tour =>
        tour.name.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Funktion, die das Öffnen/Schließen eines List-Items (Tour) ermöglicht
    const handleTourClick = (tourId) => {
        onSelectTour(tourId); // tourId übergeben, um den Zustand zu aktualisieren
    };

    // Funktion zum Bearbeiten einer Tour
    const handleEditTour = (tourId) => {
        setEditingTourId(prevId => (prevId === tourId ? null : tourId)); // Toggle Bearbeitung
    };

    return (
        <div>
            <h2>Tour List</h2>
            {/* Suchfeld */}
            <input
                type="text"
                placeholder="Search tours..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                style={{ marginBottom: '10px', width: '100%' }}
            />
            {/* Tour-Liste */}
            <ul>
                {filteredTours.map((tour) => (
                    <li key={tour.id}>
                        <div onClick={() => handleTourClick(tour.id)} style={{ cursor: 'pointer' }}>
                            {tour.name}
                        </div>
                        {/* Aufgefächerte Details und Logs anzeigen, wenn die Tour ausgewählt ist */}
                        {expandedTourIds.includes(tour.id) && ( // Überprüfen, ob die Tour ausgeklappt ist
                            <div>
                                <TourDetails tour={tour} />
                                <LogList tourId={tour.id} />
                                {/* Update-Formular anzeigen, wenn die Tour bearbeitet wird */}
                                {editingTourId === tour.id ? (
                                    <TourUpdate
                                        tour={tour}
                                        onUpdateTour={onUpdateTour}
                                        onCancel={() => handleEditTour(tour.id)} // Schließe das Update-Formular
                                    />
                                ) : (
                                    <button onClick={() => handleEditTour(tour.id)}>Edit Tour</button>
                                )}
                            </div>
                        )}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default TourList;
