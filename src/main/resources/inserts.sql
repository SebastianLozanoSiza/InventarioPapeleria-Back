INSERT INTO categorias (id_categoria, nombre, fecha_registro) VALUES
(1, 'Papelería General', NOW()),
(2, 'Tecnología', NOW());

INSERT INTO proveedores (id_proveedor, nombre, correo, telefono) VALUES
(1, 'Distribuidora El Papelito', 'contacto@papelito.com', '3014567890'),
(2, 'TecnoSuministros S.A.S', 'ventas@tecnosuministros.com', '3109876543');

INSERT INTO productos (id_producto, nombre, descripcion, stock_actual, stock_minimo, precio_compra, precio_venta, fecha_registro, id_categoria) VALUES
(1, 'Cuaderno Norma', 'Cuaderno universitario de 100 hojas', 50, 10, 2500, 3500, NOW(), 1),
(2, 'Memoria USB 16GB', 'Memoria USB marca Kingston', 20, 5, 18000, 25000, NOW(), 2);

INSERT INTO entradas (id_entrada, fecha, total, id_proveedor) VALUES
(1, NOW(), 125000, 1),
(2, NOW(), 360000, 2);

INSERT INTO detalle_entradas (id_detalle_entrada, cantidad, precio_unitario, id_entrada, id_producto) VALUES
(1, 30, 2500, 1, 1),
(2, 20, 18000, 2, 2);

INSERT INTO salidas (id_salida, motivo_salida, fecha) VALUES
(1, 'Venta mostrador', NOW()),
(2, 'Daño en bodega', NOW());

INSERT INTO detalle_salidas (id_detalle_salida, cantidad, precio_unitario, id_salida, id_producto) VALUES
(1, 2, 3500, 1, 1),
(2, 1, 25000, 2, 2);
