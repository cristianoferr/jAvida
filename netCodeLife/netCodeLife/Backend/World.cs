using System;
using System.Collections.Generic;
using System.Text;

namespace Backend
{
    public class World
    {
        IList<Mnemonic> mnemonics = new List<Mnemonic>();
        public void AddMnemonic(Mnemonic mne)
        {
            mnemonics.Add(mne);
        }
    }
}
