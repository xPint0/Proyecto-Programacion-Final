-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-06-2023 a las 21:54:44
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dim_gf`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `autores`
--

CREATE TABLE `autores` (
  `id_autor` int(11) NOT NULL COMMENT 'PK',
  `nombre_autor` varchar(100) NOT NULL,
  `id_pais` int(11) NOT NULL COMMENT 'FK -> paises'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `autores`
--

INSERT INTO `autores` (`id_autor`, `nombre_autor`, `id_pais`) VALUES
(1, 'Leonardo Da Vinci', 7),
(2, 'Vicent Van Gogh', 10),
(3, 'Edvard Munch', 9),
(4, 'Pablo Picasso', 1),
(5, 'Johannes Vermeer', 10),
(6, 'Ivan Grohar', 11),
(7, 'René Magritte', 5),
(8, 'Gustav Klimt', 4),
(9, 'Caspar David Friederich', 12),
(10, 'Hugo Simberg', 6),
(11, 'Francisco de Goya', 1),
(12, 'Gregorio Fernández', 1),
(13, 'Luis Salvador Carmona', 1),
(14, 'Desconocido', 1),
(15, 'Torcuato Ruiz del Peral', 1),
(16, 'Diego Márquez', 1),
(17, 'Juan de Juni', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `museos`
--

CREATE TABLE `museos` (
  `id_museo` int(11) NOT NULL COMMENT 'PK',
  `nombre_museo` varchar(100) NOT NULL,
  `imagen` varchar(250) NOT NULL,
  `id_pais` int(11) DEFAULT NULL COMMENT 'FK -> paises'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `museos`
--

INSERT INTO `museos` (`id_museo`, `nombre_museo`, `imagen`, `id_pais`) VALUES
(1, 'Santa María de la Gracia', '', 7),
(2, 'MoMa', '', 8),
(3, 'Galería Nacional de Noruega', '', 9),
(4, 'Museo Nacional Centro de Arte Reina Sofía', '', 1),
(5, 'Galería Real de Pinturas Mauritshuis', '', 10),
(6, 'Museo de Bellas Artes de la Villa de París', '', 2),
(7, 'Museo Magritte de Bruselas', '', 5),
(8, 'Galería Belvedere', '', 4),
(9, 'Museo Kunsthalle', '', 3),
(10, 'Museo Ateneum', '', 6),
(11, 'Museo Nacional del Prado', '', 1),
(12, 'Museo Nacional de Escultura', '', 1),
(13, 'Museo de la Catedral de la Almudena', '', 1),
(14, 'Museo de la Catedral de Granada', '', 1),
(15, 'Museo Santa Clara Gandía', '', 1),
(16, 'Museo Bellas Artes de Valencia', '', 1),
(17, 'Museo Conventual de Antequera', '', 1),
(18, 'Museo de Semana Santa de Zamora', '', 1),
(19, 'Museo de la Catedral de Oviedo', '', 1),
(24, 'Museo de Fernando Alonso', '.\\\\src\\\\main\\\\java\\\\recursos\\\\FernandoAlonso.jpg', 1),
(25, 'Museo del Champú', '.\\\\src\\\\main\\\\java\\\\recursos\\\\Champu.jpg', NULL),
(26, 'Museo del Inodoro', '.\\\\src\\\\main\\\\java\\\\recursos\\\\Inodoro.jpg', 13),
(27, 'Museo del Alambre de púas', '.\\\\src\\\\main\\\\java\\\\recursos\\\\AlambreDePuas.jpg', 8),
(28, 'Museo del Ratoncito Pérez', '.\\\\src\\\\main\\\\java\\\\recursos\\\\RatoncitoPerez.png', 1),
(29, 'Museo del Jamón de Bellota', '.\\\\src\\\\main\\\\java\\\\recursos\\\\Jamon.jpg', NULL),
(30, 'Museo del Champú', '', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `obras`
--

CREATE TABLE `obras` (
  `id_obra` int(11) NOT NULL COMMENT 'PK',
  `nombre_obra` varchar(100) NOT NULL,
  `descripción_obra` varchar(650) NOT NULL,
  `disciplina` varchar(100) NOT NULL,
  `imagen` varchar(250) NOT NULL,
  `id_museo` int(11) NOT NULL COMMENT 'FK -> museos',
  `id_autor` int(11) NOT NULL COMMENT 'FK -> autores'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `obras`
--

INSERT INTO `obras` (`id_obra`, `nombre_obra`, `descripción_obra`, `disciplina`, `imagen`, `id_museo`, `id_autor`) VALUES
(1, 'La ultima cena', 'La última cena es una pintura mural realizada por Leonardo Da Vinci entre 1495 y 1498. Se encuentra en el refectorio del convento de Santa Maria delle Grazie, en Milán, Italia. \r\nRepresenta el momento en que Jesús anuncia a sus discípulos que uno de ellos lo traicionará.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\LaUltimaCena.png', 1, 1),
(2, 'La noche estrellada', 'La noche estrellada es una de las obras más famosas del pintor holandés Vicent Van Gogh. Fue pintada en 1889, cuando el artista se encontraba en un sanatorio de Saint-Rémy-de-Provence, Francia. La pintura representa una vista nocturna del cielo y el paisaje desde la ventana de su habitación. Se puede apreciar el contraste entre el movimiento y la luz de las estrellas y la luna, y la quietud y la oscuridad de los árboles y las casas. La noche estrellada se encuentra actualmente en el Museo de Arte Moderno (MoMA) de Nueva York, Estados Unidos.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\LaNocheEstrellada.png', 2, 2),
(3, 'El grito', 'El grito es una pintura del artista noruego Edvard Munch. Se considera una de las obras más representativas del expresionismo. La pintura muestra a una persona en un puente con una expresión de angustia y horror, mientras que el cielo y el agua tienen colores intensos y contrastantes. El grito se encuentra en la Galería Nacional de Noruega, en Oslo.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\ElGrito.png', 3, 3),
(4, 'Guernica', 'Guernica es una de las obras más famosas de Pablo Picasso. Es una pintura al óleo que representa el bombardeo de la ciudad vasca de Guernica durante la Guerra Civil Española. La pintura muestra el horror y el sufrimiento de las víctimas, usando símbolos como el toro, el caballo y la paloma. Guernica se encuentra actualmente en el Museo Nacional Centro de Arte Reina Sofía en Madrid.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\Guernica.png', 4, 4),
(5, 'La joven de la Perla', 'La joven de la Perla es una famosa pintura del artista holandés Johannes Vermeer. Se cree que fue pintada alrededor de 1665 y representa a una joven con un turbante y un pendiente de perla. La pintura se encuentra actualmente en la Galería Real de Pinturas Mauritshuis en La Haya, Países Bajos. Es considerada una de las obras maestras de Vermeer y del arte barroco.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\LaJovenDeLaPerla.png', 5, 5),
(6, 'Primavera', 'Primavera es una pintura del artista esloveno Ivan Grohar, realizada en 1903. Representa un paisaje rural con un manzano en flor y una iglesia al fondo. La obra forma parte de la colección del Petit Palais, Museo de Bellas Artes de la Villa de París, en Francia. Es una de las obras más famosas de Grohar y del impresionismo esloveno.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\Primavera.png', 6, 6),
(7, 'El hijo del hombre', 'El hijo del hombre es una pintura surrealista del artista belga René Magritte. Se trata de un autorretrato en el que el rostro del pintor está oculto por una manzana verde. La obra se encuentra en el Museo Magritte de Bruselas, junto con otras obras del mismo autor. La pintura representa la dificultad de conocer la verdadera identidad de las personas y la ambigüedad de la percepción humana.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\ElHijoDelHombre.png', 7, 7),
(8, 'El beso', 'El beso es una pintura al óleo y pan de oro realizada por el artista austríaco Gustav Klimt entre 1907 y 1908 . Es una de las obras más famosas y representativas del simbolismo, un movimiento artístico que buscaba expresar ideas y emociones mediante símbolos visuales. El beso muestra a una pareja abrazada en un campo de flores, rodeados de un fondo dorado que evoca el arte bizantino . La pintura mide 1,80 metros de alto por 1,80 metros de largo y se encuentra actualmente en la Galería Belvedere en el Palacio de Belvedere en Viena, Austria.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\ElBeso.png', 8, 8),
(9, 'El caminante sobre el mar de nubes', 'El caminante sobre el mar de nubes es una obra maestra del pintor romántico alemán Caspar David Friedrich. Fue pintada en 1818 con óleo sobre tela y mide 74,8 x 94,8 cm. Se encuentra en el museo de arte Kunsthalle de Hamburgo, en Alemania. La pintura muestra a un hombre de espaldas que contempla un paisaje montañoso cubierto por una espesa niebla. El hombre representa al propio artista y su búsqueda de la comunión con la naturaleza y lo divino.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\ElCaminanteSobreElMarDeNubes.png', 9, 9),
(10, 'El ángel herido', 'El ángel herido es una pintura simbólica del artista finlandés Hugo Simberg. Fue realizada en 1903 y se encuentra en el museo Ateneum de Helsinki. La pintura muestra a un ángel con los ojos vendados y una ala ensangrentada, que es llevado por dos jóvenes de luto. Simberg nunca explicó el significado de la obra, sino que dejó que cada espectador la interpretara a su manera. La pintura fue elegida como \"La Pintura Nacional\" de Finlandia en una votación organizada por el museo Ateneum en 2006.\r\n', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\ElAngelHerido.png', 10, 10),
(11, 'El fusilamiento del 3 de mayo', 'El fusilamiento del 3 de Mayo es una obra maestra del pintor español Francisco de Goya. Se encuentra en el Museo Nacional del Prado, en Madrid. La pintura representa la masacre de los rebeldes madrileños por las tropas francesas durante la guerra de la Independencia. Goya muestra el contraste entre la crueldad de los soldados y el heroísmo de los civiles.', 'Pintura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\ElFusilamientodel3deMayo.png', 11, 11),
(12, 'Camino del Calvario', 'El Camino del Calvario es una obra maestra de la escultura procesional del siglo XVII, realizada por Gregorio Fernández para la Cofradía penitencial de la Sagrada Pasión de Cristo de Valladolid. Representa el momento en que Jesús carga con la cruz hacia el monte Calvario, acompañado por soldados romanos, sayones y mujeres piadosas. La obra se compone de diez figuras de tamaño natural, talladas en madera policromada con gran realismo y expresividad. Se puede admirar en el Museo Nacional de Escultura de Valladolid, que alberga una de las mejores colecciones de escultura española del mundo.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\CristoCaminodelCalvarioGF.png', 12, 12),
(13, 'La Sexta Angustia', 'La Sexta Angustia es una obra maestra de la escultura barroca española, creada por Gregorio Fernández en 1616. Se trata de un conjunto escultórico que representa el momento en que la Virgen María sostiene el cuerpo de Jesús después de la crucifixión. El grupo se compone de cinco figuras: la Virgen, Cristo, el Buen Ladrón, el Mal Ladrón y un ángel. La obra se encuentra en el Museo Nacional de Escultura de Valladolid, donde se puede admirar la belleza y el realismo de las expresiones y los detalles anatómicos.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\LaSextaAngustiaGF.png', 12, 12),
(14, 'Santa Teresa de Jesús', 'Santa Teresa de Jesús fue una escritora y mística española del siglo XVI. Su vida y su obra inspiraron a muchos artistas, entre ellos a Gregorio Fernández, un escultor barroco que realizó varias imágenes de la santa. Una de ellas se puede admirar en el Museo Nacional de Escultura de Valladolid, donde se exhibe una gran colección de esculturas religiosas y civiles desde la Edad Media hasta el siglo XIX.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\SantaTeresadeJesusGF.jpg', 12, 12),
(15, 'Bautismo de Cristo', 'Bautismo de Cristo es una obra maestra del escultor barroco español Gregorio Fernández. Se encuentra en el Museo Nacional de Escultura de Valladolid, España. La escultura representa el momento en que Juan el Bautista bautiza a Jesús en el río Jordán. La obra destaca por su realismo y expresividad, así como por el uso de la policromía y los efectos de luz y agua.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\BautismodeCristoGF.jpg', 12, 12),
(16, 'Virgen de los Dolores', 'La Virgen de los dolores es una escultura de Luis Salvador Carmona que se encuentra en el Museo Catedral de la Almudena. Representa a la madre de Jesús con el corazón atravesado por siete espadas, símbolo de su dolor por la pasión y muerte de su hijo. La obra es una muestra del estilo barroco del siglo XVIII, con un gran realismo y expresividad en el rostro y las manos', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\VirgendeLosDolores.png', 13, 13),
(17, 'Cristo yacente', 'Cristo yacente es una obra maestra del escultor barroco español Gregorio Fernández. Se encuentra en el Museo Nacional de Escultura de Valladolid. Es una escultura de madera policromada que representa a Jesús muerto en su sepulcro. La obra muestra un gran realismo y expresividad, reflejando el dolor y la muerte de Cristo.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\CristoYacenteGF.jpg', 12, 12),
(18, 'Tríptico de la Natividad de la Virgen', 'El Tríptico de la Natividad de la Virgen es una obra de arte religioso que se encuentra en el Museo Catedral de la Almudena, en Madrid. Se trata de una escultura de madera policromada que representa tres escenas de la vida de la Virgen María: su nacimiento, su presentación en el templo y su matrimonio con José. El autor de la obra es desconocido, pero se cree que fue realizada en el siglo XVII, siguiendo el estilo barroco. El tríptico tiene un gran valor histórico y artístico, ya que muestra la devoción mariana de la época y la riqueza de los detalles y los colores.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\TripticoDeLaNatividadDeLaVirgen.png', 13, 14),
(19, 'Cabeza de San Juan Bautista', 'La Cabeza de San Juan Bautista es una escultura de mármol realizada por el artista Torcuato Ruiz del Peral en el siglo XVIII. Se encuentra en el Museo de la Catedral de Granada, donde se exhibe junto con otras obras de arte religioso. La escultura representa la cabeza cortada del santo, que según la tradición fue entregada a Salomé por Herodes Antipas. La obra muestra un gran realismo y expresividad en los rasgos faciales y el cabello del mártir.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\CabezadeSanJuanBautista.png', 14, 15),
(20, 'Cristo yacente', 'El Cristo yacente es una escultura que representa el cuerpo de Jesús después de su muerte en la cruz. Es una obra de arte religioso que se encuentra en el Museo Santa Clara de Gandia, en la provincia de Valencia. El autor de la escultura es desconocido, pero se cree que data del siglo XVII. La escultura muestra el realismo y el dramatismo de la escena, con detalles como las heridas, la sangre y la expresión de dolor en el rostro de Cristo. El Cristo yacente forma parte del patrimonio cultural e histórico de Gandía y es una muestra de la devoción por la Pasión de Cristo que existía en la época.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\CristoYacente.jpg', 15, 14),
(21, 'Bautismo de Cristo', 'Bautismo de Cristo es una escultura anónima del siglo XVII que se encuentra en el Museo de Bellas Artes de Valencia. Representa el momento en que Juan el Bautista bautiza a Jesús en el río Jordán. La obra muestra un gran realismo y expresividad en las figuras y los ropajes.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\BautismoDeCristo.jpg', 16, 14),
(22, 'Santa Teresa de Jesús', 'Santa Teresa de Jesus fue una escritora y mística española del siglo XVI. Su vida y obra han inspirado a muchos artistas, entre ellos a Diego Márquez, un escultor contemporáneo que ha creado una serie de obras dedicadas a la santa. Una de estas obras se encuentra en el Museo Conventual Antequera, un antiguo convento de la orden carmelita fundado por la propia Teresa. La escultura representa el rostro de Teresa en un estado de éxtasis, con una expresión de paz y serenidad.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\SantaTeresaDeJesus.jpg', 17, 16),
(23, 'Jesús Camino del Calvario', 'Jesús Camino del Calvario (“Cinco de copas”) es una escultura religiosa que representa el momento en que Simón Cireneo ayuda a Jesús a llevar la cruz. La obra se encuentra en el Museo de Semana Santa de Zamora y forma parte del patrimonio de la Cofradía de Jesús Nazareno vulgo Congregación. La imagen de Jesús es de autor desconocido y data de 1802, mientras que las figuras del sayón, el centurión y los soldados fueron realizadas por Justo Fernández en 1839. La escultura tiene un gran valor artístico e histórico y es una de las más veneradas por los zamoranos.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\CristoCaminodelCalvario.jpg', 18, 14),
(24, 'Virgen de la Dolorosa', 'La Virgen de la dolorosa es una obra maestra del escultor renacentista Juan de Juni. Se encuentra en el Museo de la Catedral de Oviedo, en España. Es una escultura de madera policromada que representa a la Virgen María con el rostro lleno de dolor por la muerte de su hijo Jesús. La expresión de la Virgen es muy realista y conmovedora, y muestra la habilidad de Juan de Juni para captar las emociones humanas.', 'Escultura', '.\\\\src\\\\main\\\\java\\\\recursos\\\\VirgenDeLaDolorosa.png', 19, 17);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paises`
--

CREATE TABLE `paises` (
  `id_pais` int(11) NOT NULL,
  `nombre_pais` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `paises`
--

INSERT INTO `paises` (`id_pais`, `nombre_pais`) VALUES
(1, 'España'),
(2, 'Francia'),
(3, 'Alemania'),
(4, 'Austria'),
(5, 'Bélgica'),
(6, 'Finlandia'),
(7, 'Italia'),
(8, 'Estados Unidos'),
(9, 'Noruega'),
(10, 'Países Bajos'),
(11, 'Eslovenia'),
(12, 'Suecia'),
(13, 'India');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ranking`
--

CREATE TABLE `ranking` (
  `id_jugador` int(11) NOT NULL,
  `nombre_jugador` varchar(30) NOT NULL,
  `puntos` int(11) NOT NULL,
  `tiempo` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `autores`
--
ALTER TABLE `autores`
  ADD PRIMARY KEY (`id_autor`),
  ADD KEY `id_pais` (`id_pais`) USING BTREE;

--
-- Indices de la tabla `museos`
--
ALTER TABLE `museos`
  ADD PRIMARY KEY (`id_museo`),
  ADD KEY `id_pais` (`id_pais`) USING BTREE;

--
-- Indices de la tabla `obras`
--
ALTER TABLE `obras`
  ADD PRIMARY KEY (`id_obra`),
  ADD KEY `id_museo` (`id_museo`),
  ADD KEY `id_autor` (`id_autor`);

--
-- Indices de la tabla `paises`
--
ALTER TABLE `paises`
  ADD PRIMARY KEY (`id_pais`);

--
-- Indices de la tabla `ranking`
--
ALTER TABLE `ranking`
  ADD PRIMARY KEY (`id_jugador`),
  ADD UNIQUE KEY `nombre_jugador` (`nombre_jugador`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `autores`
--
ALTER TABLE `autores`
  MODIFY `id_autor` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK', AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `museos`
--
ALTER TABLE `museos`
  MODIFY `id_museo` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK', AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `obras`
--
ALTER TABLE `obras`
  MODIFY `id_obra` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PK', AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `paises`
--
ALTER TABLE `paises`
  MODIFY `id_pais` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `ranking`
--
ALTER TABLE `ranking`
  MODIFY `id_jugador` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `autores`
--
ALTER TABLE `autores`
  ADD CONSTRAINT `autores_ibfk_1` FOREIGN KEY (`id_pais`) REFERENCES `paises` (`id_pais`);

--
-- Filtros para la tabla `museos`
--
ALTER TABLE `museos`
  ADD CONSTRAINT `museos_ibfk_1` FOREIGN KEY (`id_pais`) REFERENCES `paises` (`id_pais`);

--
-- Filtros para la tabla `obras`
--
ALTER TABLE `obras`
  ADD CONSTRAINT `obras_ibfk_1` FOREIGN KEY (`id_museo`) REFERENCES `museos` (`id_museo`),
  ADD CONSTRAINT `obras_ibfk_2` FOREIGN KEY (`id_autor`) REFERENCES `autores` (`id_autor`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
