
INSERT INTO category (name) VALUES
                                    ( 'Toner'),
                                    ( 'Cream'),
                                    ( 'Serum'),
                                    ( 'Essence'),
                                    ( 'Sun Protection');


INSERT INTO product (name, description, price, category_id, image_path) VALUES
                                                                                ( 'B5 Toner', 'Hydrating toner with vitamin B5 for dry skin', 19.99, 1, 'b5-toner.jpg'),
                                                                                ( 'BB Cream', 'Light coverage BB cream with moisturizing properties', 29.99, 2, 'bb-cream.jpg'),
                                                                                ( 'Ceramide Serum', 'Repairing serum rich in ceramides', 39.99, 3, 'ceramide-serum.jpg'),
                                                                                ( 'Hyaluron Serum', 'Deep hydration with hyaluronic acid', 24.99, 3, 'hyaluron-serum.jpg'),
                                                                                ( 'Snail Essence', 'K-beauty classic essence with snail mucin', 22.99, 4, 'snail-essence.jpg'),
                                                                                ( 'SPF50 Cream', 'High protection daily sunscreen cream', 27.99, 5, 'spf50-cream.jpg'),
                                                                                ( 'Classic Toner', 'Gentle toner for everyday use', 17.99, 1, 'toner.jpg'),
                                                                                ('Toner Mist', 'Spray-on mist toner with refreshing formula', 18.99, 1, 'toner-mist.jpg');


INSERT INTO users (username, firstname, lastname, address, email, password_hash, role) VALUES
     ('admin', 'User', 'AdminUser', '123 Admin Street', 'admin@webshop.com', '$2a$10$OjJZ8MMGMGr5Bm7iGBFp.esntKWCYeur8btsBSOCU6U4yz3isd1qG', 'ADMIN'),
     ('pero', 'perica', 'peric', 'Vukovarska 2', 'test@test.hr','$2a$10$7ma6AONa9ihfNJ5M4hNVY.kWoHQXSXwSTYZN.ql/dMsoRDHZFXAs.', 'USER'),
     ('ivana', 'ivancica', 'ivic', 'Heinzlova', 'iva@test.hr','$2a$10$NcGTokicWkpTdOAbuG80zOS3VtRfP85A0VZaYPrdkqmhTwzTmLfXW', 'USER');

INSERT INTO paymentmethod (payment_name) VALUES ('PayPal'), ('Pouzeće')
