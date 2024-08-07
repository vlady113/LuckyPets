using System;

namespace LuckyPets
{
    public class TransaccionDTO
    {
        public long TransaccionID { get; set; }
        public long UsuarioID { get; set; }
        public long ClienteID { get; set; }
        public long ReservaID { get; set; }
        public decimal MontoCR { get; set; }
        public string Tipo { get; set; }
        public DateTime Fecha { get; set; }
    }
}
