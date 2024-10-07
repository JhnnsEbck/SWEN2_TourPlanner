/*import React, { useEffect, useRef } from 'react';
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

    return <div id="map" style={{ position: 'fixed', top: 0, left: 0, height: "100vh", width: "100vw", zIndex: 1 }}></div>;
};

export default Map;
*/

import React from 'react';
import { MapContainer, TileLayer } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';

const LeafletMap = () => {
    const position = [48.2082,16.3738];

    return (
        <div style={{ height: '100%', width: '100%' }}>
            <MapContainer center={position} zoom={13} style={{ height: '100%', width: '100%' }}>
                <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                />
            </MapContainer>
        </div>
    );
};

export default LeafletMap;
