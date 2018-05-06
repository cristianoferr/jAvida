namespace netAvida
{
    partial class TierraViewer
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle6 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle7 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle8 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle9 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle10 = new System.Windows.Forms.DataGridViewCellStyle();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabMemoria = new System.Windows.Forms.TabPage();
            this.panel4 = new System.Windows.Forms.Panel();
            this.lblOrgs = new System.Windows.Forms.Label();
            this.lblRatio = new System.Windows.Forms.Label();
            this.btnRunSingleThread = new System.Windows.Forms.Button();
            this.btnRun = new System.Windows.Forms.Button();
            this.tabDebug = new System.Windows.Forms.TabPage();
            this.panel3 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.btnStep = new System.Windows.Forms.Button();
            this.tabConfig = new System.Windows.Forms.TabPage();
            this.panelDraw = new System.Windows.Forms.Panel();
            this.dataGridOrgs = new System.Windows.Forms.DataGridView();
            this.Org = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colHash = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colSize = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colError = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colSP = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.tabControl1.SuspendLayout();
            this.tabMemoria.SuspendLayout();
            this.panel4.SuspendLayout();
            this.tabDebug.SuspendLayout();
            this.panel3.SuspendLayout();
            this.panel2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridOrgs)).BeginInit();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabMemoria);
            this.tabControl1.Controls.Add(this.tabDebug);
            this.tabControl1.Controls.Add(this.tabConfig);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Left;
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(290, 677);
            this.tabControl1.TabIndex = 0;
            // 
            // tabMemoria
            // 
            this.tabMemoria.Controls.Add(this.panel4);
            this.tabMemoria.Controls.Add(this.lblRatio);
            this.tabMemoria.Controls.Add(this.btnRunSingleThread);
            this.tabMemoria.Controls.Add(this.btnRun);
            this.tabMemoria.Location = new System.Drawing.Point(4, 22);
            this.tabMemoria.Name = "tabMemoria";
            this.tabMemoria.Padding = new System.Windows.Forms.Padding(3);
            this.tabMemoria.Size = new System.Drawing.Size(282, 651);
            this.tabMemoria.TabIndex = 0;
            this.tabMemoria.Text = "Memoria";
            this.tabMemoria.UseVisualStyleBackColor = true;
            // 
            // panel4
            // 
            this.panel4.Controls.Add(this.lblOrgs);
            this.panel4.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel4.Location = new System.Drawing.Point(3, 82);
            this.panel4.Name = "panel4";
            this.panel4.Size = new System.Drawing.Size(276, 33);
            this.panel4.TabIndex = 8;
            // 
            // lblOrgs
            // 
            this.lblOrgs.AutoSize = true;
            this.lblOrgs.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblOrgs.Location = new System.Drawing.Point(0, 0);
            this.lblOrgs.Name = "lblOrgs";
            this.lblOrgs.Size = new System.Drawing.Size(39, 13);
            this.lblOrgs.TabIndex = 0;
            this.lblOrgs.Text = "lblOrgs";
            // 
            // lblRatio
            // 
            this.lblRatio.AutoSize = true;
            this.lblRatio.Dock = System.Windows.Forms.DockStyle.Top;
            this.lblRatio.Location = new System.Drawing.Point(3, 69);
            this.lblRatio.Name = "lblRatio";
            this.lblRatio.Size = new System.Drawing.Size(42, 13);
            this.lblRatio.TabIndex = 7;
            this.lblRatio.Text = "lblRatio";
            // 
            // btnRunSingleThread
            // 
            this.btnRunSingleThread.Dock = System.Windows.Forms.DockStyle.Top;
            this.btnRunSingleThread.Location = new System.Drawing.Point(3, 36);
            this.btnRunSingleThread.Name = "btnRunSingleThread";
            this.btnRunSingleThread.Size = new System.Drawing.Size(276, 33);
            this.btnRunSingleThread.TabIndex = 6;
            this.btnRunSingleThread.Text = "Start Single Thread";
            this.btnRunSingleThread.UseVisualStyleBackColor = true;
            this.btnRunSingleThread.Click += new System.EventHandler(this.btnRunSingleThread_Click);
            // 
            // btnRun
            // 
            this.btnRun.Dock = System.Windows.Forms.DockStyle.Top;
            this.btnRun.Location = new System.Drawing.Point(3, 3);
            this.btnRun.Name = "btnRun";
            this.btnRun.Size = new System.Drawing.Size(276, 33);
            this.btnRun.TabIndex = 5;
            this.btnRun.Text = "Start";
            this.btnRun.UseVisualStyleBackColor = true;
            this.btnRun.Click += new System.EventHandler(this.btnRun_Click);
            // 
            // tabDebug
            // 
            this.tabDebug.Controls.Add(this.panel3);
            this.tabDebug.Location = new System.Drawing.Point(4, 22);
            this.tabDebug.Name = "tabDebug";
            this.tabDebug.Size = new System.Drawing.Size(282, 608);
            this.tabDebug.TabIndex = 2;
            this.tabDebug.Text = "Debug";
            this.tabDebug.UseVisualStyleBackColor = true;
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.panel2);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panel3.Location = new System.Drawing.Point(0, 0);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(282, 608);
            this.panel3.TabIndex = 1;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.btnStep);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(282, 41);
            this.panel2.TabIndex = 1;
            // 
            // btnStep
            // 
            this.btnStep.Dock = System.Windows.Forms.DockStyle.Left;
            this.btnStep.Location = new System.Drawing.Point(0, 0);
            this.btnStep.Name = "btnStep";
            this.btnStep.Size = new System.Drawing.Size(110, 41);
            this.btnStep.TabIndex = 2;
            this.btnStep.Text = "Step";
            this.btnStep.UseVisualStyleBackColor = true;
            // 
            // tabConfig
            // 
            this.tabConfig.Location = new System.Drawing.Point(4, 22);
            this.tabConfig.Name = "tabConfig";
            this.tabConfig.Padding = new System.Windows.Forms.Padding(3);
            this.tabConfig.Size = new System.Drawing.Size(282, 608);
            this.tabConfig.TabIndex = 1;
            this.tabConfig.Text = "Config";
            this.tabConfig.UseVisualStyleBackColor = true;
            // 
            // panelDraw
            // 
            this.panelDraw.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.panelDraw.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panelDraw.Location = new System.Drawing.Point(290, 0);
            this.panelDraw.Name = "panelDraw";
            this.panelDraw.Size = new System.Drawing.Size(1447, 677);
            this.panelDraw.TabIndex = 3;
            // 
            // dataGridOrgs
            // 
            this.dataGridOrgs.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridOrgs.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.Org,
            this.colID,
            this.colHash,
            this.colSize,
            this.colError,
            this.colSP});
            this.dataGridOrgs.Dock = System.Windows.Forms.DockStyle.Right;
            this.dataGridOrgs.Location = new System.Drawing.Point(1496, 0);
            this.dataGridOrgs.Name = "dataGridOrgs";
            this.dataGridOrgs.Size = new System.Drawing.Size(241, 677);
            this.dataGridOrgs.TabIndex = 4;
            // 
            // Org
            // 
            this.Org.HeaderText = "Org";
            this.Org.Name = "Org";
            this.Org.ReadOnly = true;
            this.Org.Visible = false;
            // 
            // colID
            // 
            dataGridViewCellStyle6.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle6.Format = "N0";
            dataGridViewCellStyle6.NullValue = null;
            this.colID.DefaultCellStyle = dataGridViewCellStyle6;
            this.colID.HeaderText = "oID";
            this.colID.Name = "colID";
            this.colID.ReadOnly = true;
            this.colID.Width = 30;
            // 
            // colHash
            // 
            dataGridViewCellStyle7.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle7.Format = "N0";
            dataGridViewCellStyle7.NullValue = null;
            this.colHash.DefaultCellStyle = dataGridViewCellStyle7;
            this.colHash.HeaderText = "Hash";
            this.colHash.Name = "colHash";
            this.colHash.ReadOnly = true;
            this.colHash.Width = 75;
            // 
            // colSize
            // 
            dataGridViewCellStyle8.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle8.Format = "N0";
            dataGridViewCellStyle8.NullValue = null;
            this.colSize.DefaultCellStyle = dataGridViewCellStyle8;
            this.colSize.HeaderText = "Size";
            this.colSize.Name = "colSize";
            this.colSize.ReadOnly = true;
            this.colSize.Width = 30;
            // 
            // colError
            // 
            dataGridViewCellStyle9.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle9.Format = "N0";
            this.colError.DefaultCellStyle = dataGridViewCellStyle9;
            this.colError.HeaderText = "Error";
            this.colError.Name = "colError";
            this.colError.ReadOnly = true;
            this.colError.Width = 30;
            // 
            // colSP
            // 
            dataGridViewCellStyle10.Format = "N0";
            dataGridViewCellStyle10.NullValue = null;
            this.colSP.DefaultCellStyle = dataGridViewCellStyle10;
            this.colSP.HeaderText = "SP";
            this.colSP.Name = "colSP";
            this.colSP.ReadOnly = true;
            this.colSP.Width = 30;
            // 
            // TierraViewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1737, 677);
            this.Controls.Add(this.dataGridOrgs);
            this.Controls.Add(this.panelDraw);
            this.Controls.Add(this.tabControl1);
            this.Name = "TierraViewer";
            this.Text = "TierraViewer";
            this.tabControl1.ResumeLayout(false);
            this.tabMemoria.ResumeLayout(false);
            this.tabMemoria.PerformLayout();
            this.panel4.ResumeLayout(false);
            this.panel4.PerformLayout();
            this.tabDebug.ResumeLayout(false);
            this.panel3.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridOrgs)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabMemoria;
        private System.Windows.Forms.TabPage tabConfig;
        private System.Windows.Forms.TabPage tabDebug;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Panel panel2;
        public System.Windows.Forms.Button btnStep;
        private System.Windows.Forms.Panel panel4;
        private System.Windows.Forms.Label lblOrgs;
        private System.Windows.Forms.Label lblRatio;
        public System.Windows.Forms.Button btnRunSingleThread;
        public System.Windows.Forms.Button btnRun;
        public System.Windows.Forms.Panel panelDraw;
        public System.Windows.Forms.DataGridView dataGridOrgs;
        private System.Windows.Forms.DataGridViewTextBoxColumn Org;
        private System.Windows.Forms.DataGridViewTextBoxColumn colID;
        private System.Windows.Forms.DataGridViewTextBoxColumn colHash;
        private System.Windows.Forms.DataGridViewTextBoxColumn colSize;
        private System.Windows.Forms.DataGridViewTextBoxColumn colError;
        private System.Windows.Forms.DataGridViewTextBoxColumn colSP;
    }
}