package com.vectorinc.moniepointchallenge.ui.shipment

val sampleShipments = listOf(
    Shipment(
        id = "#12345",
        origin = "Warehouse A",
        price = "$19.99",
        date = "2024-09-01",
        status = ShipmentStatus.InProgress,
        iconUrl = "https://via.placeholder.com/64"
    ),
    Shipment(
        id = "#12346",
        origin = "Warehouse B",
        price = "$25.50",
        date = "2024-09-02",
        status = ShipmentStatus.Completed,
        iconUrl = "https://via.placeholder.com/64"
    ),
    Shipment(
        id = "#12347",
        origin = "Warehouse C",
        price = "$12.75",
        date = "2024-09-03",
        status = ShipmentStatus.Pending,
        iconUrl = "https://via.placeholder.com/64"
    ),
    Shipment(
        id = "#12348",
        origin = "Warehouse D",
        price = "$9.99",
        date = "2024-09-04",
        status = ShipmentStatus.Loading,
        iconUrl = "https://via.placeholder.com/64"
    )
)