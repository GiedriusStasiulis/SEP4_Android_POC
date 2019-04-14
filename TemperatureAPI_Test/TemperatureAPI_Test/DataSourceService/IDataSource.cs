using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace TemperatureAPI_Test.DataSourceService
{
    public interface IDataSource
    {
        void InitDataSource();
        Task DoWorkAsync();
        string GetData();
    }
}
