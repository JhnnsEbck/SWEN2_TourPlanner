import React, { useEffect, useRef } from 'react';
import L from 'leaflet';
import 'leaflet/dist/leaflet.css';

const Map = ({ coordinates }) => {
    const mapRef = useRef(null);

    useEffect(() => {
        mapRef.current = L.map('map', {
            center: [51.505, -0.09],
            zoom: 13,
            layers: [
                L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                    attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                }),
            ],
        });
    }, []);

    useEffect(() => {
        if (coordinates && coordinates.length > 0) {
            coordinates.forEach((coordinate) => {
                L.marker([coordinate[1], coordinate[0]]).addTo(mapRef.current);
            });
        }
    }, [coordinates]);

    return <div id="map" style={{ height: "100vh", width: "100%" }}></div>;
};

export default Map;