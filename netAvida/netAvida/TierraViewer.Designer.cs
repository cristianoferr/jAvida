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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle4 = new System.Windows.Forms.DataGridViewCellStyle();
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabMemoria = new System.Windows.Forms.TabPage();
            this.panelDraw = new System.Windows.Forms.Panel();
            this.panel4 = new System.Windows.Forms.Panel();
            this.lblRatio = new System.Windows.Forms.Label();
            this.lblOrgs = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.btnRunSingleThread = new System.Windows.Forms.Button();
            this.btnRun = new System.Windows.Forms.Button();
            this.tabDebug = new System.Windows.Forms.TabPage();
            this.dataGridRuns = new System.Windows.Forms.DataGridView();
            this.colSize = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colHash = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colID = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.colError = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.panel3 = new System.Windows.Forms.Panel();
            this.panel2 = new System.Windows.Forms.Panel();
            this.btnStep = new System.Windows.Forms.Button();
            this.tabConfig = new System.Windows.Forms.TabPage();
            this.tabControl1.SuspendLayout();
            this.tabMemoria.SuspendLayout();
            this.panel4.SuspendLayout();
            this.panel1.SuspendLayout();
            this.tabDebug.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridRuns)).BeginInit();
            this.panel3.SuspendLayout();
            this.panel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabMemoria);
            this.tabControl1.Controls.Add(this.tabDebug);
            this.tabControl1.Controls.Add(this.tabConfig);
            this.tabControl1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tabControl1.Location = new System.Drawing.Point(0, 0);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(1025, 634);
            this.tabControl1.TabIndex = 0;
            // 
            // tabMemoria
            // 
            this.tabMemoria.Controls.Add(this.panelDraw);
            this.tabMemoria.Controls.Add(this.panel4);
            this.tabMemoria.Controls.Add(this.panel1);
            this.tabMemoria.Location = new System.Drawing.Point(4, 22);
            this.tabMemoria.Name = "tabMemoria";
            this.tabMemoria.Padding = new System.Windows.Forms.Padding(3);
            this.tabMemoria.Size = new System.Drawing.Size(1017, 608);
            this.tabMemoria.TabIndex = 0;
            this.tabMemoria.Text = "Memoria";
            this.tabMemoria.UseVisualStyleBackColor = true;
            // 
            // panelDraw
            // 
            this.panelDraw.AutoSizeMode = System.Windows.Forms.AutoSizeMode.GrowAndShrink;
            this.panelDraw.Dock = System.Windows.Forms.DockStyle.Fill;
            this.panelDraw.Location = new System.Drawing.Point(203, 36);
            this.panelDraw.Name = "panelDraw";
            this.panelDraw.Size = new System.Drawing.Size(811, 569);
            this.panelDraw.TabIndex = 2;
            // 
            // panel4
            // 
            this.panel4.Controls.Add(this.lblRatio);
            this.panel4.Controls.Add(this.lblOrgs);
            this.panel4.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel4.Location = new System.Drawing.Point(203, 3);
            this.panel4.Name = "panel4";
            this.panel4.Size = new System.Drawing.Size(811, 33);
            this.panel4.TabIndex = 3;
            // 
            // lblRatio
            // 
            this.lblRatio.AutoSize = true;
            this.lblRatio.Dock = System.Windows.Forms.DockStyle.Left;
            this.lblRatio.Location = new System.Drawing.Point(35, 0);
            this.lblRatio.Name = "lblRatio";
            this.lblRatio.Size = new System.Drawing.Size(35, 13);
            this.lblRatio.TabIndex = 1;
            this.lblRatio.Text = "label1";
            // 
            // lblOrgs
            // 
            this.lblOrgs.AutoSize = true;
            this.lblOrgs.Dock = System.Windows.Forms.DockStyle.Left;
            this.lblOrgs.Location = new System.Drawing.Point(0, 0);
            this.lblOrgs.Name = "lblOrgs";
            this.lblOrgs.Size = new System.Drawing.Size(35, 13);
            this.lblOrgs.TabIndex = 0;
            this.lblOrgs.Text = "label1";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.btnRunSingleThread);
            this.panel1.Controls.Add(this.btnRun);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel1.Location = new System.Drawing.Point(3, 3);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(200, 602);
            this.panel1.TabIndex = 1;
            // 
            // btnRunSingleThread
            // 
            this.btnRunSingleThread.Dock = System.Windows.Forms.DockStyle.Top;
            this.btnRunSingleThread.Location = new System.Drawing.Point(0, 33);
            this.btnRunSingleThread.Name = "btnRunSingleThread";
            this.btnRunSingleThread.Size = new System.Drawing.Size(200, 33);
            this.btnRunSingleThread.TabIndex = 2;
            this.btnRunSingleThread.Text = "Start Single Thread";
            this.btnRunSingleThread.UseVisualStyleBackColor = true;
            this.btnRunSingleThread.Click += new System.EventHandler(this.btnRunSingleThread_Click);
            // 
            // btnRun
            // 
            this.btnRun.Dock = System.Windows.Forms.DockStyle.Top;
            this.btnRun.Location = new System.Drawing.Point(0, 0);
            this.btnRun.Name = "btnRun";
            this.btnRun.Size = new System.Drawing.Size(200, 33);
            this.btnRun.TabIndex = 1;
            this.btnRun.Text = "Start";
            this.btnRun.UseVisualStyleBackColor = true;
            this.btnRun.Click += new System.EventHandler(this.btnRun_Click);
            // 
            // tabDebug
            // 
            this.tabDebug.Controls.Add(this.dataGridRuns);
            this.tabDebug.Controls.Add(this.panel3);
            this.tabDebug.Location = new System.Drawing.Point(4, 22);
            this.tabDebug.Name = "tabDebug";
            this.tabDebug.Size = new System.Drawing.Size(1017, 608);
            this.tabDebug.TabIndex = 2;
            this.tabDebug.Text = "Debug";
            this.tabDebug.UseVisualStyleBackColor = true;
            // 
            // dataGridRuns
            // 
            this.dataGridRuns.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridRuns.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.colSize,
            this.colHash,
            this.colID,
            this.colError});
            this.dataGridRuns.Dock = System.Windows.Forms.DockStyle.Fill;
            this.dataGridRuns.Location = new System.Drawing.Point(498, 0);
            this.dataGridRuns.Name = "dataGridRuns";
            this.dataGridRuns.Size = new System.Drawing.Size(519, 608);
            this.dataGridRuns.TabIndex = 2;
            // 
            // colSize
            // 
            dataGridViewCellStyle1.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle1.Format = "N0";
            dataGridViewCellStyle1.NullValue = null;
            this.colSize.DefaultCellStyle = dataGridViewCellStyle1;
            this.colSize.HeaderText = "Size";
            this.colSize.Name = "colSize";
            this.colSize.ReadOnly = true;
            this.colSize.Width = 30;
            // 
            // colHash
            // 
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle2.Format = "N0";
            dataGridViewCellStyle2.NullValue = null;
            this.colHash.DefaultCellStyle = dataGridViewCellStyle2;
            this.colHash.HeaderText = "Hash";
            this.colHash.Name = "colHash";
            this.colHash.ReadOnly = true;
            this.colHash.Width = 50;
            // 
            // colID
            // 
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle3.Format = "N0";
            dataGridViewCellStyle3.NullValue = null;
            this.colID.DefaultCellStyle = dataGridViewCellStyle3;
            this.colID.HeaderText = "oID";
            this.colID.Name = "colID";
            this.colID.ReadOnly = true;
            this.colID.Width = 50;
            // 
            // colError
            // 
            dataGridViewCellStyle4.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleRight;
            dataGridViewCellStyle4.Format = "N0";
            this.colError.DefaultCellStyle = dataGridViewCellStyle4;
            this.colError.HeaderText = "Error";
            this.colError.Name = "colError";
            this.colError.ReadOnly = true;
            // 
            // panel3
            // 
            this.panel3.Controls.Add(this.panel2);
            this.panel3.Dock = System.Windows.Forms.DockStyle.Left;
            this.panel3.Location = new System.Drawing.Point(0, 0);
            this.panel3.Name = "panel3";
            this.panel3.Size = new System.Drawing.Size(498, 608);
            this.panel3.TabIndex = 1;
            // 
            // panel2
            // 
            this.panel2.Controls.Add(this.btnStep);
            this.panel2.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(498, 41);
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
            this.tabConfig.Size = new System.Drawing.Size(1017, 608);
            this.tabConfig.TabIndex = 1;
            this.tabConfig.Text = "Config";
            this.tabConfig.UseVisualStyleBackColor = true;
            // 
            // TierraViewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1025, 634);
            this.Controls.Add(this.tabControl1);
            this.Name = "TierraViewer";
            this.Text = "TierraViewer";
            this.tabControl1.ResumeLayout(false);
            this.tabMemoria.ResumeLayout(false);
            this.panel4.ResumeLayout(false);
            this.panel4.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.tabDebug.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridRuns)).EndInit();
            this.panel3.ResumeLayout(false);
            this.panel2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabMemoria;
        private System.Windows.Forms.TabPage tabConfig;
        private System.Windows.Forms.Panel panel1;
        public System.Windows.Forms.Button btnRun;
        public System.Windows.Forms.Button btnRunSingleThread;
        private System.Windows.Forms.TabPage tabDebug;
        public System.Windows.Forms.Panel panelDraw;
        public System.Windows.Forms.DataGridView dataGridRuns;
        private System.Windows.Forms.DataGridViewTextBoxColumn colSize;
        private System.Windows.Forms.DataGridViewTextBoxColumn colHash;
        private System.Windows.Forms.DataGridViewTextBoxColumn colID;
        private System.Windows.Forms.DataGridViewTextBoxColumn colError;
        private System.Windows.Forms.Panel panel3;
        private System.Windows.Forms.Panel panel2;
        public System.Windows.Forms.Button btnStep;
        private System.Windows.Forms.Panel panel4;
        private System.Windows.Forms.Label lblOrgs;
        private System.Windows.Forms.Label lblRatio;
    }
}