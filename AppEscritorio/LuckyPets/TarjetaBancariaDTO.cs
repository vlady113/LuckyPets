using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LuckyPets
{
    internal class TarjetaBancariaDTO
    {
        public long NumeroTarjeta { get; set; }
        public DateTime FechaCaducidad { get; set; }
        public string TitularTarjeta { get; set; }
        public string EmisorTarjeta { get; set; }
        public int Cvv { get; set; }
        public string ImgTarjeta { get; set; }
        public string UsuarioEmail { get; set; }
        public UsuarioDTO Usuario { get; set; }
    }

    public class UsuarioDTO
    {
        public long UserID { get; set; }
        public string Email { get; set; }
    }
}