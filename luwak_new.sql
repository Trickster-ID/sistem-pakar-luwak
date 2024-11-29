-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Oct 31, 2018 at 09:46 AM
-- Server version: 10.1.10-MariaDB
-- PHP Version: 5.6.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `luwak`
--

-- --------------------------------------------------------

--
-- Table structure for table `gejala`
--

CREATE TABLE `gejala` (
  `id_gejala` char(3) NOT NULL,
  `nama_gejala` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gejala`
--

INSERT INTO `gejala` (`id_gejala`, `nama_gejala`) VALUES
('G01', 'Terlihat parasit di sekitar telinga'),
('G02', 'Luwak menggaruk-garuk badannya'),
('G03', 'Bulu rontok'),
('G04', 'Kulit melipat-lipat'),
('G05', 'Warna kemerahan di sekitar kerontokan'),
('G06', 'Kulit lembap'),
('G07', 'Nafsu makan berkurang'),
('G08', 'Lesu'),
('G09', 'Feses cair'),
('G10', 'Feses beraroma busuk tidak seperti biasa'),
('G11', 'Batuk / bronkitis'),
('G12', 'Bersin-bersin'),
('G13', 'Keluar cairan dari hidung'),
('G14', 'Sesak Nafas'),
('G15', 'Sering buang air kecil'),
('G16', 'Haus meningkat'),
('G17', 'Kelaparan meningkat'),
('G18', 'Jika mempunyai luka, tidak kunjung kering'),
('G19', 'Gelisah'),
('G20', 'Keluarnya organ dari anus atau kemaluan'),
('G21', 'Menjilati bagian organ yang keluar'),
('G22', 'Perut tegang dan keras'),
('G23', 'Keputihan dibagian kemaaluan hingga menetes'),
('G24', 'Hidung terlihat basah'),
('G25', 'Terkadang ada liur yang keluar dari mulut luwak'),
('G26', 'Terlihat sulit bernafas');

-- --------------------------------------------------------

--
-- Table structure for table `penyakit`
--

CREATE TABLE `penyakit` (
  `id_penyakit` char(3) NOT NULL,
  `nama_penyakit` varchar(50) NOT NULL,
  `solusi` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penyakit`
--

INSERT INTO `penyakit` (`id_penyakit`, `nama_penyakit`, `solusi`) VALUES
('P01', 'Scabies / Penyakit Kulit', 'Diobati bisa dengan cara tradisional yaitu ambil 1 botol Minyak Tawon + 1 sendok Belerang halus kemudian di kocok hingga berwarna putih, kemudian dioleskan dengan sikat gigi di tempat scabies 2 kali sehari. Bisa juga dengan cara membeli obat Permethrin di apotik atau toko online.'),
('P02', 'Ringworm / Jamur Kulit', 'Obati dengan obat Permethrin (obat luar untuk kulit) yang bisa didapatkan di apotik terdekat, dengan cara basahkan kapas dengan air dan peras, lalu teteskan obat dan usapkan dengan kuat pada tempat yang berjamur.'),
('P03', 'Coronavirus / Infeksi Saluran Pencernaan', 'Pengobatan dapat dilakukan dengan cara membawa luwak ke dokter hewan yang kemudian divaksinasi primary yang bertujuan guna meningkatkan daya tahan dan kekebalan tubuh.'),
('P04', 'Parainfluensa / Infeksi Saluran Pernapasan', 'Pengobatan dapat dilakukan dengan cara membawa luwak ke dokter hewan yang kemudian divaksinasi influensa dan diberikan vitamin C dan antibiotik.'),
('P05', 'Pneumonia / Radang Paru-paru', 'Pengobatannya harus segera dibawa ke dokter hewan untuk penanganan karena penyakit tersebut tergoolong penyakit berat'),
('P06', 'Diabetes Mellitus / Gula Darah Tinggi', 'Pengobatannya harus segera dibawa ke dokter hewan untuk penanganan karena penyakit tersebut tergoolong penyakit berat'),
('P07', 'Propalus / Keluarnya organ bagian dalam', 'Pengobatannya harus segera dibawa ke dokter hewan untuk penanganan karena penyakit tersebut tergoolong penyakit berat'),
('P08', 'Fluor Albus / Keputihan', 'Berikan Obat Amoxicillin(obat dalam untuk anti bakteri) dengan dosis 5-10mg/kg per 12 jam, kemudian berikan makanan yang rendah protein dan bertekstur lembut serta mudah dicerna.'),
('P09', 'Rhinitis / Pilek', 'Mandikan luwak dengan air hangat, kemudian jemur selama beberapa menit, hindari sinar matahari langsung kepada mata luwak. Bersihkan kandang luwak secara menyeluruh kemudian jemur kandang luwak selama 10-15 menit, jaga kebersihkan kandang adalah hal terpenting. Dan yang terakhir beri pepaya atau pisang sebagai makanan untuk luwak.');

-- --------------------------------------------------------

--
-- Table structure for table `relasi`
--

CREATE TABLE `relasi` (
  `id_relasi` int(3) NOT NULL,
  `id_gejala` char(3) NOT NULL,
  `id_penyakit` char(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `relasi`
--

INSERT INTO `relasi` (`id_relasi`, `id_gejala`, `id_penyakit`) VALUES
(1, 'G01', 'P01'),
(2, 'G02', 'P01'),
(3, 'G03', 'P01'),
(4, 'G04', 'P01'),
(5, 'G03', 'P02'),
(6, 'G05', 'P02'),
(7, 'G06', 'P02'),
(8, 'G07', 'P03'),
(9, 'G08', 'P03'),
(10, 'G09', 'P03'),
(11, 'G10', 'P03'),
(12, 'G11', 'P04'),
(13, 'G12', 'P04'),
(14, 'G11', 'P05'),
(15, 'G13', 'P05'),
(16, 'G14', 'P05'),
(17, 'G15', 'P06'),
(18, 'G16', 'P06'),
(19, 'G17', 'P06'),
(20, 'G18', 'P06'),
(21, 'G19', 'P07'),
(22, 'G20', 'P07'),
(23, 'G21', 'P07'),
(24, 'G22', 'P07'),
(25, 'G08', 'P08'),
(26, 'G07', 'P08'),
(27, 'G23', 'P08'),
(28, 'G07', 'P09'),
(29, 'G12', 'P09'),
(30, 'G24', 'P09'),
(31, 'G25', 'P09'),
(32, 'G26', 'P04');

-- --------------------------------------------------------

--
-- Table structure for table `tmp_analisa`
--

CREATE TABLE `tmp_analisa` (
  `id` int(3) NOT NULL,
  `id_gejala` char(3) NOT NULL,
  `id_penyakit` char(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `tmp_gejala`
--

CREATE TABLE `tmp_gejala` (
  `id` int(3) NOT NULL,
  `id_gejala` char(3) NOT NULL,
  `pil` enum('y','n') NOT NULL DEFAULT 'n'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `gejala`
--
ALTER TABLE `gejala`
  ADD PRIMARY KEY (`id_gejala`);

--
-- Indexes for table `penyakit`
--
ALTER TABLE `penyakit`
  ADD PRIMARY KEY (`id_penyakit`);

--
-- Indexes for table `relasi`
--
ALTER TABLE `relasi`
  ADD PRIMARY KEY (`id_relasi`),
  ADD KEY `id_gejala` (`id_gejala`),
  ADD KEY `id_penyakit` (`id_penyakit`);

--
-- Indexes for table `tmp_analisa`
--
ALTER TABLE `tmp_analisa`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tmp_gejala`
--
ALTER TABLE `tmp_gejala`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `relasi`
--
ALTER TABLE `relasi`
  MODIFY `id_relasi` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;
--
-- AUTO_INCREMENT for table `tmp_analisa`
--
ALTER TABLE `tmp_analisa`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `tmp_gejala`
--
ALTER TABLE `tmp_gejala`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `relasi`
--
ALTER TABLE `relasi`
  ADD CONSTRAINT `relasi_ibfk_1` FOREIGN KEY (`id_gejala`) REFERENCES `gejala` (`id_gejala`),
  ADD CONSTRAINT `relasi_ibfk_2` FOREIGN KEY (`id_penyakit`) REFERENCES `penyakit` (`id_penyakit`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
